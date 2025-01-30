package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.WidgetContainer;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.GuidelineReference;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ConstraintLayout extends ViewGroup {
    public static SharedValues sSharedValues;
    public final SparseArray mChildrenByIds;
    public final ArrayList mConstraintHelpers;
    public ConstraintLayoutStates mConstraintLayoutSpec;
    public ConstraintSet mConstraintSet;
    public int mConstraintSetId;
    public HashMap mDesignIds;
    public boolean mDirtyHierarchy;
    public final ConstraintWidgetContainer mLayoutWidget;
    public int mMaxHeight;
    public int mMaxWidth;
    public final Measurer mMeasurer;
    public int mMinHeight;
    public int mMinWidth;
    public int mOnMeasureHeightMeasureSpec;
    public int mOnMeasureWidthMeasureSpec;
    public int mOptimizationLevel;
    public final SparseArray mTempMapIdToWidget;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.widget.ConstraintLayout$1 */
    public abstract /* synthetic */ class AbstractC01411 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour */
        public static final /* synthetic */ int[] f31x6d00e4a2;

        static {
            int[] iArr = new int[ConstraintWidget.DimensionBehaviour.values().length];
            f31x6d00e4a2 = iArr;
            try {
                iArr[ConstraintWidget.DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f31x6d00e4a2[ConstraintWidget.DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f31x6d00e4a2[ConstraintWidget.DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f31x6d00e4a2[ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Measurer implements BasicMeasure.Measurer {
        public final ConstraintLayout layout;
        public int layoutHeightSpec;
        public int layoutWidthSpec;
        public int paddingBottom;
        public int paddingHeight;
        public int paddingTop;
        public int paddingWidth;

        public Measurer(ConstraintLayout constraintLayout) {
            this.layout = constraintLayout;
        }

        public static boolean isSimilarSpec(int i, int i2, int i3) {
            if (i == i2) {
                return true;
            }
            int mode = View.MeasureSpec.getMode(i);
            View.MeasureSpec.getSize(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (mode2 == 1073741824) {
                return (mode == Integer.MIN_VALUE || mode == 0) && i3 == size;
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:138:0x028d  */
        /* JADX WARN: Removed duplicated region for block: B:140:0x0298  */
        /* JADX WARN: Removed duplicated region for block: B:142:0x029d  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x0294  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void measure(ConstraintWidget constraintWidget, BasicMeasure.Measure measure) {
            int makeMeasureSpec;
            int makeMeasureSpec2;
            int baseline;
            int i;
            int i2;
            int i3;
            int i4;
            boolean z;
            int measuredWidth;
            int i5;
            int childMeasureSpec;
            if (constraintWidget == null) {
                return;
            }
            if (constraintWidget.mVisibility == 8 && !constraintWidget.inPlaceholder) {
                measure.measuredWidth = 0;
                measure.measuredHeight = 0;
                measure.measuredBaseline = 0;
                return;
            }
            if (constraintWidget.mParent == null) {
                return;
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = measure.horizontalBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = measure.verticalBehavior;
            int i6 = measure.horizontalDimension;
            int i7 = measure.verticalDimension;
            int i8 = this.paddingTop + this.paddingBottom;
            int i9 = this.paddingWidth;
            View view = (View) constraintWidget.mCompanionWidget;
            int[] iArr = AbstractC01411.f31x6d00e4a2;
            int i10 = iArr[dimensionBehaviour.ordinal()];
            ConstraintAnchor constraintAnchor = constraintWidget.mRight;
            ConstraintAnchor constraintAnchor2 = constraintWidget.mLeft;
            if (i10 != 1) {
                if (i10 == 2) {
                    childMeasureSpec = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, i9, -2);
                } else if (i10 == 3) {
                    int i11 = this.layoutWidthSpec;
                    int i12 = constraintAnchor2 != null ? constraintAnchor2.mMargin + 0 : 0;
                    if (constraintAnchor != null) {
                        i12 += constraintAnchor.mMargin;
                    }
                    childMeasureSpec = ViewGroup.getChildMeasureSpec(i11, i9 + i12, -1);
                } else if (i10 != 4) {
                    makeMeasureSpec = 0;
                } else {
                    makeMeasureSpec = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, i9, -2);
                    boolean z2 = constraintWidget.mMatchConstraintDefaultWidth == 1;
                    int i13 = measure.measureStrategy;
                    if (i13 == 1 || i13 == 2) {
                        if (measure.measureStrategy == 2 || !z2 || (z2 && (view.getMeasuredHeight() == constraintWidget.getHeight())) || (view instanceof Placeholder) || constraintWidget.isResolvedHorizontally()) {
                            childMeasureSpec = View.MeasureSpec.makeMeasureSpec(constraintWidget.getWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                        }
                    }
                }
                makeMeasureSpec = childMeasureSpec;
            } else {
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            }
            int i14 = iArr[dimensionBehaviour2.ordinal()];
            if (i14 == 1) {
                makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i7, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            } else if (i14 == 2) {
                makeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, i8, -2);
            } else if (i14 == 3) {
                int i15 = this.layoutHeightSpec;
                int i16 = constraintAnchor2 != null ? constraintWidget.mTop.mMargin + 0 : 0;
                if (constraintAnchor != null) {
                    i16 += constraintWidget.mBottom.mMargin;
                }
                makeMeasureSpec2 = ViewGroup.getChildMeasureSpec(i15, i8 + i16, -1);
            } else if (i14 != 4) {
                makeMeasureSpec2 = 0;
            } else {
                makeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, i8, -2);
                boolean z3 = constraintWidget.mMatchConstraintDefaultHeight == 1;
                int i17 = measure.measureStrategy;
                if (i17 == 1 || i17 == 2) {
                    if (measure.measureStrategy == 2 || !z3 || (z3 && (view.getMeasuredWidth() == constraintWidget.getWidth())) || (view instanceof Placeholder) || constraintWidget.isResolvedVertically()) {
                        makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(constraintWidget.getHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    }
                }
            }
            ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget.mParent;
            ConstraintLayout constraintLayout = ConstraintLayout.this;
            if (constraintWidgetContainer != null && Optimizer.enabled(constraintLayout.mOptimizationLevel, 256) && view.getMeasuredWidth() == constraintWidget.getWidth() && view.getMeasuredWidth() < constraintWidgetContainer.getWidth() && view.getMeasuredHeight() == constraintWidget.getHeight() && view.getMeasuredHeight() < constraintWidgetContainer.getHeight() && view.getBaseline() == constraintWidget.mBaselineDistance && !constraintWidget.isMeasureRequested()) {
                if (isSimilarSpec(constraintWidget.mLastHorizontalMeasureSpec, makeMeasureSpec, constraintWidget.getWidth()) && isSimilarSpec(constraintWidget.mLastVerticalMeasureSpec, makeMeasureSpec2, constraintWidget.getHeight())) {
                    measure.measuredWidth = constraintWidget.getWidth();
                    measure.measuredHeight = constraintWidget.getHeight();
                    measure.measuredBaseline = constraintWidget.mBaselineDistance;
                    return;
                }
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            boolean z4 = dimensionBehaviour == dimensionBehaviour3;
            boolean z5 = dimensionBehaviour2 == dimensionBehaviour3;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            boolean z6 = dimensionBehaviour2 == dimensionBehaviour4 || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED;
            boolean z7 = dimensionBehaviour == dimensionBehaviour4 || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED;
            boolean z8 = z4 && constraintWidget.mDimensionRatio > 0.0f;
            boolean z9 = z5 && constraintWidget.mDimensionRatio > 0.0f;
            if (view == null) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int i18 = measure.measureStrategy;
            if (i18 != 1 && i18 != 2 && z4 && constraintWidget.mMatchConstraintDefaultWidth == 0 && z5 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                measuredWidth = 0;
                i5 = -1;
                z = false;
                baseline = 0;
                i2 = 0;
            } else {
                if ((view instanceof VirtualLayout) && (constraintWidget instanceof androidx.constraintlayout.core.widgets.VirtualLayout)) {
                    ((VirtualLayout) view).onMeasure((androidx.constraintlayout.core.widgets.VirtualLayout) constraintWidget, makeMeasureSpec, makeMeasureSpec2);
                } else {
                    view.measure(makeMeasureSpec, makeMeasureSpec2);
                }
                constraintWidget.mLastHorizontalMeasureSpec = makeMeasureSpec;
                constraintWidget.mLastVerticalMeasureSpec = makeMeasureSpec2;
                constraintWidget.mMeasureRequested = false;
                int measuredWidth2 = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                baseline = view.getBaseline();
                int i19 = constraintWidget.mMatchConstraintMinWidth;
                int max = i19 > 0 ? Math.max(i19, measuredWidth2) : measuredWidth2;
                int i20 = constraintWidget.mMatchConstraintMaxWidth;
                if (i20 > 0) {
                    max = Math.min(i20, max);
                }
                int i21 = constraintWidget.mMatchConstraintMinHeight;
                if (i21 > 0) {
                    i2 = Math.max(i21, measuredHeight);
                    i = makeMeasureSpec2;
                } else {
                    i = makeMeasureSpec2;
                    i2 = measuredHeight;
                }
                int i22 = constraintWidget.mMatchConstraintMaxHeight;
                if (i22 > 0) {
                    i2 = Math.min(i22, i2);
                }
                if (!Optimizer.enabled(constraintLayout.mOptimizationLevel, 1)) {
                    if (z8 && z6) {
                        i3 = (int) ((i2 * constraintWidget.mDimensionRatio) + 0.5f);
                        if (measuredWidth2 == i3 || measuredHeight != i2) {
                            if (measuredWidth2 == i3) {
                                i4 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                            } else {
                                i4 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                            }
                            int makeMeasureSpec3 = measuredHeight == i2 ? View.MeasureSpec.makeMeasureSpec(i2, i4) : i;
                            view.measure(makeMeasureSpec, makeMeasureSpec3);
                            constraintWidget.mLastHorizontalMeasureSpec = makeMeasureSpec;
                            constraintWidget.mLastVerticalMeasureSpec = makeMeasureSpec3;
                            z = false;
                            constraintWidget.mMeasureRequested = false;
                            measuredWidth = view.getMeasuredWidth();
                            i2 = view.getMeasuredHeight();
                            baseline = view.getBaseline();
                            i5 = -1;
                        } else {
                            measuredWidth = i3;
                            i5 = -1;
                            z = false;
                        }
                    } else if (z9 && z7) {
                        i2 = (int) ((max / constraintWidget.mDimensionRatio) + 0.5f);
                    }
                }
                i3 = max;
                if (measuredWidth2 == i3) {
                }
                if (measuredWidth2 == i3) {
                }
                if (measuredHeight == i2) {
                }
                view.measure(makeMeasureSpec, makeMeasureSpec3);
                constraintWidget.mLastHorizontalMeasureSpec = makeMeasureSpec;
                constraintWidget.mLastVerticalMeasureSpec = makeMeasureSpec3;
                z = false;
                constraintWidget.mMeasureRequested = false;
                measuredWidth = view.getMeasuredWidth();
                i2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
                i5 = -1;
            }
            boolean z10 = baseline != i5 ? true : z;
            measure.measuredNeedsSolverPass = (measuredWidth == measure.horizontalDimension && i2 == measure.verticalDimension) ? z : true;
            boolean z11 = layoutParams.needsBaseline ? true : z10;
            if (z11 && baseline != -1 && constraintWidget.mBaselineDistance != baseline) {
                measure.measuredNeedsSolverPass = true;
            }
            measure.measuredWidth = measuredWidth;
            measure.measuredHeight = i2;
            measure.measuredHasBaseline = z11;
            measure.measuredBaseline = baseline;
        }
    }

    public ConstraintLayout(Context context) {
        super(context);
        this.mChildrenByIds = new SparseArray();
        this.mConstraintHelpers = new ArrayList(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap();
        this.mTempMapIdToWidget = new SparseArray();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(null, 0, 0);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x02d0 -> B:78:0x02d1). Please report as a decompilation issue!!! */
    public final void applyConstraintsFromLayoutParams(boolean z, View view, ConstraintWidget constraintWidget, LayoutParams layoutParams, SparseArray sparseArray) {
        float f;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5;
        int i;
        int i2;
        float f2;
        int i3;
        float f3;
        layoutParams.validate();
        constraintWidget.mVisibility = view.getVisibility();
        if (layoutParams.isInPlaceholder) {
            constraintWidget.inPlaceholder = true;
            constraintWidget.mVisibility = 8;
        }
        constraintWidget.mCompanionWidget = view;
        if (view instanceof ConstraintHelper) {
            ((ConstraintHelper) view).resolveRtl(constraintWidget, this.mLayoutWidget.mIsRtl);
        }
        int i4 = -1;
        if (layoutParams.isGuideline) {
            androidx.constraintlayout.core.widgets.Guideline guideline = (androidx.constraintlayout.core.widgets.Guideline) constraintWidget;
            int i5 = layoutParams.resolvedGuideBegin;
            int i6 = layoutParams.resolvedGuideEnd;
            float f4 = layoutParams.resolvedGuidePercent;
            if (f4 != -1.0f) {
                if (f4 > -1.0f) {
                    guideline.mRelativePercent = f4;
                    guideline.mRelativeBegin = -1;
                    guideline.mRelativeEnd = -1;
                    return;
                }
                return;
            }
            if (i5 != -1) {
                if (i5 > -1) {
                    guideline.mRelativePercent = -1.0f;
                    guideline.mRelativeBegin = i5;
                    guideline.mRelativeEnd = -1;
                    return;
                }
                return;
            }
            if (i6 == -1 || i6 <= -1) {
                return;
            }
            guideline.mRelativePercent = -1.0f;
            guideline.mRelativeBegin = -1;
            guideline.mRelativeEnd = i6;
            return;
        }
        int i7 = layoutParams.resolvedLeftToLeft;
        int i8 = layoutParams.resolvedLeftToRight;
        int i9 = layoutParams.resolvedRightToLeft;
        int i10 = layoutParams.resolvedRightToRight;
        int i11 = layoutParams.resolveGoneLeftMargin;
        int i12 = layoutParams.resolveGoneRightMargin;
        float f5 = layoutParams.resolvedHorizontalBias;
        int i13 = layoutParams.circleConstraint;
        if (i13 != -1) {
            ConstraintWidget constraintWidget6 = (ConstraintWidget) sparseArray.get(i13);
            if (constraintWidget6 != null) {
                float f6 = layoutParams.circleAngle;
                int i14 = layoutParams.circleRadius;
                ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
                f3 = 0.0f;
                constraintWidget.immediateConnect(type, constraintWidget6, type, i14, 0);
                constraintWidget.mCircleConstraintAngle = f6;
            } else {
                f3 = 0.0f;
            }
            f = f3;
        } else {
            if (i7 != -1) {
                ConstraintWidget constraintWidget7 = (ConstraintWidget) sparseArray.get(i7);
                if (constraintWidget7 != null) {
                    ConstraintAnchor.Type type2 = ConstraintAnchor.Type.LEFT;
                    f = 0.0f;
                    constraintWidget.immediateConnect(type2, constraintWidget7, type2, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i11);
                } else {
                    f = 0.0f;
                }
            } else {
                f = 0.0f;
                if (i8 != -1 && (constraintWidget2 = (ConstraintWidget) sparseArray.get(i8)) != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, constraintWidget2, ConstraintAnchor.Type.RIGHT, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i11);
                }
            }
            if (i9 != -1) {
                ConstraintWidget constraintWidget8 = (ConstraintWidget) sparseArray.get(i9);
                if (constraintWidget8 != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, constraintWidget8, ConstraintAnchor.Type.LEFT, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, i12);
                }
            } else if (i10 != -1 && (constraintWidget3 = (ConstraintWidget) sparseArray.get(i10)) != null) {
                ConstraintAnchor.Type type3 = ConstraintAnchor.Type.RIGHT;
                constraintWidget.immediateConnect(type3, constraintWidget3, type3, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, i12);
            }
            int i15 = layoutParams.topToTop;
            if (i15 != -1) {
                ConstraintWidget constraintWidget9 = (ConstraintWidget) sparseArray.get(i15);
                if (constraintWidget9 != null) {
                    ConstraintAnchor.Type type4 = ConstraintAnchor.Type.TOP;
                    constraintWidget.immediateConnect(type4, constraintWidget9, type4, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, layoutParams.goneTopMargin);
                }
            } else {
                int i16 = layoutParams.topToBottom;
                if (i16 != -1 && (constraintWidget4 = (ConstraintWidget) sparseArray.get(i16)) != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, constraintWidget4, ConstraintAnchor.Type.BOTTOM, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, layoutParams.goneTopMargin);
                }
            }
            int i17 = layoutParams.bottomToTop;
            if (i17 != -1) {
                ConstraintWidget constraintWidget10 = (ConstraintWidget) sparseArray.get(i17);
                if (constraintWidget10 != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, constraintWidget10, ConstraintAnchor.Type.TOP, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, layoutParams.goneBottomMargin);
                }
            } else {
                int i18 = layoutParams.bottomToBottom;
                if (i18 != -1 && (constraintWidget5 = (ConstraintWidget) sparseArray.get(i18)) != null) {
                    ConstraintAnchor.Type type5 = ConstraintAnchor.Type.BOTTOM;
                    constraintWidget.immediateConnect(type5, constraintWidget5, type5, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, layoutParams.goneBottomMargin);
                }
            }
            int i19 = layoutParams.baselineToBaseline;
            if (i19 != -1) {
                setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i19, ConstraintAnchor.Type.BASELINE);
            } else {
                int i20 = layoutParams.baselineToTop;
                if (i20 != -1) {
                    setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i20, ConstraintAnchor.Type.TOP);
                } else {
                    int i21 = layoutParams.baselineToBottom;
                    if (i21 != -1) {
                        setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i21, ConstraintAnchor.Type.BOTTOM);
                    }
                }
            }
            if (f5 >= f) {
                constraintWidget.mHorizontalBiasPercent = f5;
            }
            float f7 = layoutParams.verticalBias;
            if (f7 >= f) {
                constraintWidget.mVerticalBiasPercent = f7;
            }
        }
        if (z && ((i3 = layoutParams.editorAbsoluteX) != -1 || layoutParams.editorAbsoluteY != -1)) {
            int i22 = layoutParams.editorAbsoluteY;
            constraintWidget.f15mX = i3;
            constraintWidget.f16mY = i22;
        }
        if (layoutParams.horizontalDimensionFixed) {
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setWidth(((ViewGroup.MarginLayoutParams) layoutParams).width);
            if (((ViewGroup.MarginLayoutParams) layoutParams).width == -2) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        } else if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
            if (layoutParams.constrainedWidth) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            } else {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            }
            constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        } else {
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            constraintWidget.setWidth(0);
        }
        if (layoutParams.verticalDimensionFixed) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setHeight(((ViewGroup.MarginLayoutParams) layoutParams).height);
            if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        } else if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
            if (layoutParams.constrainedHeight) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            } else {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            }
            constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        } else {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            constraintWidget.setHeight(0);
        }
        String str = layoutParams.dimensionRatio;
        if (str == null || str.length() == 0) {
            constraintWidget.mDimensionRatio = f;
        } else {
            int length = str.length();
            int indexOf = str.indexOf(44);
            if (indexOf <= 0 || indexOf >= length - 1) {
                i = 1;
                i2 = 0;
            } else {
                String substring = str.substring(0, indexOf);
                if (substring.equalsIgnoreCase("W")) {
                    i4 = 0;
                } else if (substring.equalsIgnoreCase(ImsProfile.TIMER_NAME_H)) {
                    i = 1;
                    i4 = 1;
                    i2 = indexOf + i;
                }
                i = 1;
                i2 = indexOf + i;
            }
            int indexOf2 = str.indexOf(58);
            if (indexOf2 < 0 || indexOf2 >= length - i) {
                String substring2 = str.substring(i2);
                if (substring2.length() > 0) {
                    f2 = Float.parseFloat(substring2);
                }
                f2 = f;
            } else {
                String substring3 = str.substring(i2, indexOf2);
                String substring4 = str.substring(indexOf2 + i);
                if (substring3.length() > 0 && substring4.length() > 0) {
                    float parseFloat = Float.parseFloat(substring3);
                    float parseFloat2 = Float.parseFloat(substring4);
                    if (parseFloat > f && parseFloat2 > f) {
                        f2 = i4 == 1 ? Math.abs(parseFloat2 / parseFloat) : Math.abs(parseFloat / parseFloat2);
                    }
                }
                f2 = f;
            }
            if (f2 > f) {
                constraintWidget.mDimensionRatio = f2;
                constraintWidget.mDimensionRatioSide = i4;
            }
        }
        float f8 = layoutParams.horizontalWeight;
        float[] fArr = constraintWidget.mWeight;
        fArr[0] = f8;
        fArr[1] = layoutParams.verticalWeight;
        constraintWidget.mHorizontalChainStyle = layoutParams.horizontalChainStyle;
        constraintWidget.mVerticalChainStyle = layoutParams.verticalChainStyle;
        int i23 = layoutParams.wrapBehaviorInParent;
        if (i23 >= 0 && i23 <= 3) {
            constraintWidget.mWrapBehaviorInParent = i23;
        }
        int i24 = layoutParams.matchConstraintDefaultWidth;
        int i25 = layoutParams.matchConstraintMinWidth;
        int i26 = layoutParams.matchConstraintMaxWidth;
        float f9 = layoutParams.matchConstraintPercentWidth;
        constraintWidget.mMatchConstraintDefaultWidth = i24;
        constraintWidget.mMatchConstraintMinWidth = i25;
        if (i26 == Integer.MAX_VALUE) {
            i26 = 0;
        }
        constraintWidget.mMatchConstraintMaxWidth = i26;
        constraintWidget.mMatchConstraintPercentWidth = f9;
        if (f9 > f && f9 < 1.0f && i24 == 0) {
            constraintWidget.mMatchConstraintDefaultWidth = 2;
        }
        int i27 = layoutParams.matchConstraintDefaultHeight;
        int i28 = layoutParams.matchConstraintMinHeight;
        int i29 = layoutParams.matchConstraintMaxHeight;
        float f10 = layoutParams.matchConstraintPercentHeight;
        constraintWidget.mMatchConstraintDefaultHeight = i27;
        constraintWidget.mMatchConstraintMinHeight = i28;
        constraintWidget.mMatchConstraintMaxHeight = i29 != Integer.MAX_VALUE ? i29 : 0;
        constraintWidget.mMatchConstraintPercentHeight = f10;
        if (f10 <= f || f10 >= 1.0f || i27 != 0) {
            return;
        }
        constraintWidget.mMatchConstraintDefaultHeight = 2;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Object tag;
        int size;
        ArrayList arrayList = this.mConstraintHelpers;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            for (int i = 0; i < size; i++) {
                ((ConstraintHelper) this.mConstraintHelpers.get(i)).updatePreDraw(this);
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            float width = getWidth();
            float height = getHeight();
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() != 8 && (tag = childAt.getTag()) != null && (tag instanceof String)) {
                    String[] split = ((String) tag).split(",");
                    if (split.length == 4) {
                        int parseInt = Integer.parseInt(split[0]);
                        int parseInt2 = Integer.parseInt(split[1]);
                        int parseInt3 = Integer.parseInt(split[2]);
                        int i3 = (int) ((parseInt / 1080.0f) * width);
                        int i4 = (int) ((parseInt2 / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f = i3;
                        float f2 = i4;
                        float f3 = i3 + ((int) ((parseInt3 / 1080.0f) * width));
                        canvas.drawLine(f, f2, f3, f2, paint);
                        float parseInt4 = i4 + ((int) ((Integer.parseInt(split[3]) / 1920.0f) * height));
                        canvas.drawLine(f3, f2, f3, parseInt4, paint);
                        canvas.drawLine(f3, parseInt4, f, parseInt4, paint);
                        canvas.drawLine(f, parseInt4, f, f2, paint);
                        paint.setColor(-16711936);
                        canvas.drawLine(f, f2, f3, parseInt4, paint);
                        canvas.drawLine(f, parseInt4, f3, f2, paint);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public final void forceLayout() {
        this.mDirtyHierarchy = true;
        super.forceLayout();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final View getViewById(int i) {
        return (View) this.mChildrenByIds.get(i);
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        if (view.getLayoutParams() instanceof LayoutParams) {
            return ((LayoutParams) view.getLayoutParams()).widget;
        }
        view.setLayoutParams(generateLayoutParams(view.getLayoutParams()));
        if (view.getLayoutParams() instanceof LayoutParams) {
            return ((LayoutParams) view.getLayoutParams()).widget;
        }
        return null;
    }

    public final void init(AttributeSet attributeSet, int i, int i2) {
        ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
        constraintWidgetContainer.mCompanionWidget = this;
        Measurer measurer = this.mMeasurer;
        constraintWidgetContainer.mMeasurer = measurer;
        constraintWidgetContainer.mDependencyGraph.mMeasurer = measurer;
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout, i, i2);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == 16) {
                    this.mMinWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinWidth);
                } else if (index == 17) {
                    this.mMinHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinHeight);
                } else if (index == 14) {
                    this.mMaxWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxWidth);
                } else if (index == 15) {
                    this.mMaxHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxHeight);
                } else if (index == 113) {
                    this.mOptimizationLevel = obtainStyledAttributes.getInt(index, this.mOptimizationLevel);
                } else if (index == 56) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    if (resourceId != 0) {
                        try {
                            parseLayoutDescription(resourceId);
                        } catch (Resources.NotFoundException unused) {
                            this.mConstraintLayoutSpec = null;
                        }
                    }
                } else if (index == 34) {
                    int resourceId2 = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.load(resourceId2, getContext());
                    } catch (Resources.NotFoundException unused2) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = resourceId2;
                }
            }
            obtainStyledAttributes.recycle();
        }
        ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutWidget;
        constraintWidgetContainer2.mOptimizationLevel = this.mOptimizationLevel;
        LinearSystem.USE_DEPENDENCY_ORDERING = constraintWidgetContainer2.optimizeFor(512);
    }

    public final boolean isRtl() {
        return ((getContext().getApplicationInfo().flags & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0) && 1 == getLayoutDirection();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view;
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.widget;
            if ((childAt.getVisibility() != 8 || layoutParams.isGuideline || layoutParams.isHelper || isInEditMode) && !layoutParams.isInPlaceholder) {
                int x = constraintWidget.getX();
                int y = constraintWidget.getY();
                int width = constraintWidget.getWidth() + x;
                int height = constraintWidget.getHeight() + y;
                childAt.layout(x, y, width, height);
                if ((childAt instanceof Placeholder) && (view = ((Placeholder) childAt).mContent) != null) {
                    view.setVisibility(0);
                    view.layout(x, y, width, height);
                }
            }
        }
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i6 = 0; i6 < size; i6++) {
                ((ConstraintHelper) this.mConstraintHelpers.get(i6)).updatePostLayout();
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        boolean z;
        String str;
        int findId;
        ConstraintSet constraintSet;
        HashMap hashMap;
        ConstraintWidget constraintWidget;
        int i3 = 0;
        if (!this.mDirtyHierarchy) {
            int childCount = getChildCount();
            int i4 = 0;
            while (true) {
                if (i4 >= childCount) {
                    break;
                }
                if (getChildAt(i4).isLayoutRequested()) {
                    this.mDirtyHierarchy = true;
                    break;
                }
                i4++;
            }
        }
        this.mOnMeasureWidthMeasureSpec = i;
        this.mOnMeasureHeightMeasureSpec = i2;
        this.mLayoutWidget.mIsRtl = isRtl();
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            int childCount2 = getChildCount();
            int i5 = 0;
            while (true) {
                if (i5 >= childCount2) {
                    z = false;
                    break;
                } else {
                    if (getChildAt(i5).isLayoutRequested()) {
                        z = true;
                        break;
                    }
                    i5++;
                }
            }
            if (z) {
                boolean isInEditMode = isInEditMode();
                int childCount3 = getChildCount();
                for (int i6 = 0; i6 < childCount3; i6++) {
                    ConstraintWidget viewWidget = getViewWidget(getChildAt(i6));
                    if (viewWidget != null) {
                        viewWidget.reset();
                    }
                }
                int i7 = -1;
                if (isInEditMode) {
                    for (int i8 = 0; i8 < childCount3; i8++) {
                        View childAt = getChildAt(i8);
                        try {
                            String resourceName = getResources().getResourceName(childAt.getId());
                            setDesignInformation(resourceName, Integer.valueOf(childAt.getId()));
                            int indexOf = resourceName.indexOf(47);
                            if (indexOf != -1) {
                                resourceName = resourceName.substring(indexOf + 1);
                            }
                            int id = childAt.getId();
                            if (id == 0) {
                                constraintWidget = this.mLayoutWidget;
                            } else {
                                View view = (View) this.mChildrenByIds.get(id);
                                if (view == null && (view = findViewById(id)) != null && view != this && view.getParent() == this) {
                                    onViewAdded(view);
                                }
                                constraintWidget = view == this ? this.mLayoutWidget : view == null ? null : ((LayoutParams) view.getLayoutParams()).widget;
                            }
                            constraintWidget.mDebugName = resourceName;
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                }
                if (this.mConstraintSetId != -1) {
                    int i9 = 0;
                    while (i9 < childCount3) {
                        View childAt2 = getChildAt(i9);
                        if (childAt2.getId() == this.mConstraintSetId && (childAt2 instanceof Constraints)) {
                            Constraints constraints = (Constraints) childAt2;
                            if (constraints.myConstraintSet == null) {
                                constraints.myConstraintSet = new ConstraintSet();
                            }
                            ConstraintSet constraintSet2 = constraints.myConstraintSet;
                            constraintSet2.getClass();
                            int childCount4 = constraints.getChildCount();
                            HashMap hashMap2 = constraintSet2.mConstraints;
                            hashMap2.clear();
                            int i10 = i3;
                            while (i10 < childCount4) {
                                View childAt3 = constraints.getChildAt(i10);
                                Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt3.getLayoutParams();
                                int id2 = childAt3.getId();
                                int i11 = childCount4;
                                if (constraintSet2.mForceId && id2 == i7) {
                                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                                }
                                if (!hashMap2.containsKey(Integer.valueOf(id2))) {
                                    hashMap2.put(Integer.valueOf(id2), new ConstraintSet.Constraint());
                                }
                                ConstraintSet.Constraint constraint = (ConstraintSet.Constraint) hashMap2.get(Integer.valueOf(id2));
                                if (constraint == null) {
                                    constraintSet = constraintSet2;
                                    hashMap = hashMap2;
                                } else {
                                    if (childAt3 instanceof ConstraintHelper) {
                                        ConstraintHelper constraintHelper = (ConstraintHelper) childAt3;
                                        constraint.fillFromConstraints(id2, layoutParams);
                                        if (constraintHelper instanceof Barrier) {
                                            ConstraintSet.Layout layout = constraint.layout;
                                            constraintSet = constraintSet2;
                                            layout.mHelperType = 1;
                                            Barrier barrier = (Barrier) constraintHelper;
                                            layout.mBarrierDirection = barrier.mIndicatedType;
                                            hashMap = hashMap2;
                                            layout.mReferenceIds = Arrays.copyOf(barrier.mIds, barrier.mCount);
                                            layout.mBarrierMargin = barrier.mBarrier.mMargin;
                                            constraint.fillFromConstraints(id2, layoutParams);
                                        }
                                    }
                                    constraintSet = constraintSet2;
                                    hashMap = hashMap2;
                                    constraint.fillFromConstraints(id2, layoutParams);
                                }
                                i10++;
                                childCount4 = i11;
                                constraintSet2 = constraintSet;
                                hashMap2 = hashMap;
                                i7 = -1;
                            }
                            this.mConstraintSet = constraints.myConstraintSet;
                        }
                        i9++;
                        i3 = 0;
                        i7 = -1;
                    }
                }
                ConstraintSet constraintSet3 = this.mConstraintSet;
                if (constraintSet3 != null) {
                    constraintSet3.applyToInternal(this);
                }
                this.mLayoutWidget.mChildren.clear();
                int size = this.mConstraintHelpers.size();
                if (size > 0) {
                    for (int i12 = 0; i12 < size; i12++) {
                        ConstraintHelper constraintHelper2 = (ConstraintHelper) this.mConstraintHelpers.get(i12);
                        if (constraintHelper2.isInEditMode()) {
                            constraintHelper2.setIds(constraintHelper2.mReferenceIds);
                        }
                        HelperWidget helperWidget = constraintHelper2.mHelperWidget;
                        if (helperWidget != null) {
                            helperWidget.mWidgetsCount = 0;
                            Arrays.fill(helperWidget.mWidgets, (Object) null);
                            for (int i13 = 0; i13 < constraintHelper2.mCount; i13++) {
                                int i14 = constraintHelper2.mIds[i13];
                                View viewById = getViewById(i14);
                                if (viewById == null && (findId = constraintHelper2.findId(this, (str = (String) constraintHelper2.mMap.get(Integer.valueOf(i14))))) != 0) {
                                    constraintHelper2.mIds[i13] = findId;
                                    constraintHelper2.mMap.put(Integer.valueOf(findId), str);
                                    viewById = getViewById(findId);
                                }
                                if (viewById != null) {
                                    constraintHelper2.mHelperWidget.add(getViewWidget(viewById));
                                }
                            }
                            constraintHelper2.mHelperWidget.updateConstraints();
                        }
                    }
                }
                for (int i15 = 0; i15 < childCount3; i15++) {
                    View childAt4 = getChildAt(i15);
                    if (childAt4 instanceof Placeholder) {
                        Placeholder placeholder = (Placeholder) childAt4;
                        if (placeholder.mContentId == -1 && !placeholder.isInEditMode()) {
                            placeholder.setVisibility(placeholder.mEmptyVisibility);
                        }
                        View findViewById = findViewById(placeholder.mContentId);
                        placeholder.mContent = findViewById;
                        if (findViewById != null) {
                            ((LayoutParams) findViewById.getLayoutParams()).isInPlaceholder = true;
                            placeholder.mContent.setVisibility(0);
                            placeholder.setVisibility(0);
                        }
                    }
                }
                this.mTempMapIdToWidget.clear();
                this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
                this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
                for (int i16 = 0; i16 < childCount3; i16++) {
                    View childAt5 = getChildAt(i16);
                    this.mTempMapIdToWidget.put(childAt5.getId(), getViewWidget(childAt5));
                }
                for (int i17 = 0; i17 < childCount3; i17++) {
                    View childAt6 = getChildAt(i17);
                    ConstraintWidget viewWidget2 = getViewWidget(childAt6);
                    if (viewWidget2 != null) {
                        LayoutParams layoutParams2 = (LayoutParams) childAt6.getLayoutParams();
                        ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
                        constraintWidgetContainer.mChildren.add(viewWidget2);
                        ConstraintWidget constraintWidget2 = viewWidget2.mParent;
                        if (constraintWidget2 != null) {
                            ((WidgetContainer) constraintWidget2).mChildren.remove(viewWidget2);
                            viewWidget2.reset();
                        }
                        viewWidget2.mParent = constraintWidgetContainer;
                        applyConstraintsFromLayoutParams(isInEditMode, childAt6, viewWidget2, layoutParams2, this.mTempMapIdToWidget);
                    }
                }
            }
            if (z) {
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutWidget;
                constraintWidgetContainer2.mBasicMeasureSolver.updateHierarchy(constraintWidgetContainer2);
            }
        }
        resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, i, i2);
        int width = this.mLayoutWidget.getWidth();
        int height = this.mLayoutWidget.getHeight();
        ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutWidget;
        resolveMeasuredDimension(constraintWidgetContainer3.mWidthMeasuredTooSmall, i, i2, width, height, constraintWidgetContainer3.mHeightMeasuredTooSmall);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        ConstraintWidget viewWidget = getViewWidget(view);
        if ((view instanceof Guideline) && !(viewWidget instanceof androidx.constraintlayout.core.widgets.Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            androidx.constraintlayout.core.widgets.Guideline guideline = new androidx.constraintlayout.core.widgets.Guideline();
            layoutParams.widget = guideline;
            layoutParams.isGuideline = true;
            guideline.setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.validateParams();
            ((LayoutParams) view.getLayoutParams()).isHelper = true;
            if (!this.mConstraintHelpers.contains(constraintHelper)) {
                this.mConstraintHelpers.add(constraintHelper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = true;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.mChildrenByIds.remove(view.getId());
        ConstraintWidget viewWidget = getViewWidget(view);
        this.mLayoutWidget.mChildren.remove(viewWidget);
        viewWidget.reset();
        this.mConstraintHelpers.remove(view);
        this.mDirtyHierarchy = true;
    }

    public void parseLayoutDescription(int i) {
        this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, i);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.mDirtyHierarchy = true;
        super.requestLayout();
    }

    public final void resolveMeasuredDimension(boolean z, int i, int i2, int i3, int i4, boolean z2) {
        Measurer measurer = this.mMeasurer;
        int i5 = measurer.paddingHeight;
        int resolveSizeAndState = ViewGroup.resolveSizeAndState(i3 + measurer.paddingWidth, i, 0);
        int resolveSizeAndState2 = ViewGroup.resolveSizeAndState(i4 + i5, i2, 0) & 16777215;
        int min = Math.min(this.mMaxWidth, resolveSizeAndState & 16777215);
        int min2 = Math.min(this.mMaxHeight, resolveSizeAndState2);
        if (z) {
            min |= 16777216;
        }
        if (z2) {
            min2 |= 16777216;
        }
        setMeasuredDimension(min, min2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x04f7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0576  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0582  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x044a  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01e2  */
    /* JADX WARN: Type inference failed for: r12v10, types: [int] */
    /* JADX WARN: Type inference failed for: r12v40 */
    /* JADX WARN: Type inference failed for: r12v41 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v24 */
    /* JADX WARN: Type inference failed for: r13v9 */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resolveSystem(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        int i4;
        int i5;
        int max;
        int max2;
        int width;
        DependencyGraph dependencyGraph;
        int i6;
        int i7;
        int i8;
        BasicMeasure basicMeasure;
        int size;
        boolean z;
        boolean z2;
        boolean z3;
        BasicMeasure.Measurer measurer;
        int i9;
        int i10;
        boolean z4;
        int size2;
        int i11;
        ?? r13;
        int i12;
        ArrayList arrayList;
        int i13;
        BasicMeasure.Measurer measurer2;
        int i14;
        BasicMeasure.Measurer measurer3;
        int i15;
        boolean z5;
        HorizontalWidgetRun horizontalWidgetRun;
        VerticalWidgetRun verticalWidgetRun;
        DependencyGraph dependencyGraph2;
        int i16;
        int i17;
        int i18;
        boolean z6;
        int i19;
        boolean z7;
        boolean z8;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        int mode = View.MeasureSpec.getMode(i2);
        int size3 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size4 = View.MeasureSpec.getSize(i3);
        int max3 = Math.max(0, getPaddingTop());
        int max4 = Math.max(0, getPaddingBottom());
        int i20 = max3 + max4;
        int max5 = Math.max(0, getPaddingRight()) + Math.max(0, getPaddingLeft());
        int max6 = Math.max(0, getPaddingEnd()) + Math.max(0, getPaddingStart());
        if (max6 > 0) {
            max5 = max6;
        }
        Measurer measurer4 = this.mMeasurer;
        measurer4.paddingTop = max3;
        measurer4.paddingBottom = max4;
        measurer4.paddingWidth = max5;
        measurer4.paddingHeight = i20;
        measurer4.layoutWidthSpec = i2;
        measurer4.layoutHeightSpec = i3;
        int max7 = Math.max(0, getPaddingStart());
        int max8 = Math.max(0, getPaddingEnd());
        if (max7 <= 0 && max8 <= 0) {
            max7 = Math.max(0, getPaddingLeft());
        } else if (isRtl()) {
            max7 = max8;
        }
        int i21 = size3 - max5;
        int i22 = size4 - i20;
        Measurer measurer5 = this.mMeasurer;
        int i23 = measurer5.paddingHeight;
        int i24 = measurer5.paddingWidth;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
        int childCount = getChildCount();
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (childCount == 0) {
                    max = Math.max(0, this.mMinWidth);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviour;
                    i5 = max;
                    dimensionBehaviour2 = dimensionBehaviour4;
                }
            } else {
                if (mode == 1073741824) {
                    i5 = Math.min(this.mMaxWidth - i24, i21);
                    i4 = Integer.MIN_VALUE;
                    dimensionBehaviour2 = dimensionBehaviour3;
                    if (mode2 == i4) {
                        dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        max2 = childCount == 0 ? Math.max(0, this.mMinHeight) : i22;
                    } else if (mode2 != 0) {
                        if (mode2 == 1073741824) {
                            max2 = Math.min(this.mMaxHeight - i23, i22);
                        }
                        max2 = 0;
                    } else {
                        dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        if (childCount == 0) {
                            max2 = Math.max(0, this.mMinHeight);
                        }
                        max2 = 0;
                    }
                    width = constraintWidgetContainer.getWidth();
                    dependencyGraph = constraintWidgetContainer2.mDependencyGraph;
                    if (i5 == width || max2 != constraintWidgetContainer.getHeight()) {
                        dependencyGraph.mNeedRedoMeasures = true;
                    }
                    constraintWidgetContainer2.f15mX = 0;
                    constraintWidgetContainer2.f16mY = 0;
                    int i25 = this.mMaxWidth - i24;
                    int[] iArr = constraintWidgetContainer2.mMaxDimension;
                    iArr[0] = i25;
                    iArr[1] = this.mMaxHeight - i23;
                    constraintWidgetContainer2.mMinWidth = 0;
                    constraintWidgetContainer2.mMinHeight = 0;
                    constraintWidgetContainer2.setHorizontalDimensionBehaviour(dimensionBehaviour2);
                    constraintWidgetContainer2.setWidth(i5);
                    constraintWidgetContainer2.setVerticalDimensionBehaviour(dimensionBehaviour3);
                    constraintWidgetContainer2.setHeight(max2);
                    i6 = this.mMinWidth - i24;
                    if (i6 < 0) {
                        i7 = 0;
                        constraintWidgetContainer2.mMinWidth = 0;
                    } else {
                        i7 = 0;
                        constraintWidgetContainer2.mMinWidth = i6;
                    }
                    i8 = this.mMinHeight - i23;
                    if (i8 < 0) {
                        constraintWidgetContainer2.mMinHeight = i7;
                    } else {
                        constraintWidgetContainer2.mMinHeight = i8;
                    }
                    constraintWidgetContainer2.mPaddingLeft = max7;
                    constraintWidgetContainer2.mPaddingTop = max3;
                    basicMeasure = constraintWidgetContainer2.mBasicMeasureSolver;
                    basicMeasure.getClass();
                    BasicMeasure.Measurer measurer6 = constraintWidgetContainer2.mMeasurer;
                    size = constraintWidgetContainer2.mChildren.size();
                    int width2 = constraintWidgetContainer.getWidth();
                    int height = constraintWidgetContainer.getHeight();
                    boolean enabled = Optimizer.enabled(i, 128);
                    z = !enabled || Optimizer.enabled(i, 64);
                    if (z) {
                        for (int i26 = 0; i26 < size; i26++) {
                            ConstraintWidget constraintWidget = (ConstraintWidget) constraintWidgetContainer2.mChildren.get(i26);
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = dimensionBehaviourArr[0];
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                            boolean z9 = (dimensionBehaviour5 == dimensionBehaviour6) && (dimensionBehaviourArr[1] == dimensionBehaviour6) && constraintWidget.mDimensionRatio > 0.0f;
                            if ((constraintWidget.isInHorizontalChain() && z9) || ((constraintWidget.isInVerticalChain() && z9) || (constraintWidget instanceof androidx.constraintlayout.core.widgets.VirtualLayout) || constraintWidget.isInHorizontalChain() || constraintWidget.isInVerticalChain())) {
                                z = false;
                                break;
                            }
                        }
                    }
                    z2 = z & ((mode != 1073741824 && mode2 == 1073741824) || enabled);
                    if (z2) {
                        int min = Math.min(constraintWidgetContainer2.mMaxDimension[0], i21);
                        int min2 = Math.min(constraintWidgetContainer2.mMaxDimension[1], i22);
                        if (mode != 1073741824 || constraintWidgetContainer.getWidth() == min) {
                            dependencyGraph2 = dependencyGraph;
                        } else {
                            constraintWidgetContainer2.setWidth(min);
                            dependencyGraph2 = dependencyGraph;
                            dependencyGraph2.mNeedBuildGraph = true;
                        }
                        if (mode2 == 1073741824 && constraintWidgetContainer.getHeight() != min2) {
                            constraintWidgetContainer2.setHeight(min2);
                            dependencyGraph2.mNeedBuildGraph = true;
                        }
                        if (mode == 1073741824 && mode2 == 1073741824) {
                            boolean z10 = enabled & true;
                            boolean z11 = dependencyGraph2.mNeedBuildGraph;
                            ConstraintWidgetContainer constraintWidgetContainer3 = dependencyGraph2.container;
                            if (z11 || dependencyGraph2.mNeedRedoMeasures) {
                                Iterator it = constraintWidgetContainer3.mChildren.iterator();
                                while (it.hasNext()) {
                                    ConstraintWidget constraintWidget2 = (ConstraintWidget) it.next();
                                    constraintWidget2.ensureWidgetRuns();
                                    constraintWidget2.measured = false;
                                    constraintWidget2.horizontalRun.reset();
                                    constraintWidget2.verticalRun.reset();
                                }
                                i19 = 0;
                                constraintWidgetContainer3.ensureWidgetRuns();
                                constraintWidgetContainer3.measured = false;
                                constraintWidgetContainer3.horizontalRun.reset();
                                constraintWidgetContainer3.verticalRun.reset();
                                dependencyGraph2.mNeedRedoMeasures = false;
                            } else {
                                i19 = 0;
                            }
                            dependencyGraph2.basicMeasureWidgets(dependencyGraph2.mContainer);
                            constraintWidgetContainer3.f15mX = i19;
                            constraintWidgetContainer3.f16mY = i19;
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = constraintWidgetContainer3.getDimensionBehaviour(i19);
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = constraintWidgetContainer3.getDimensionBehaviour(1);
                            if (dependencyGraph2.mNeedBuildGraph) {
                                dependencyGraph2.buildGraph();
                            }
                            int x = constraintWidgetContainer3.getX();
                            int y = constraintWidgetContainer3.getY();
                            z3 = z2;
                            constraintWidgetContainer3.horizontalRun.start.resolve(x);
                            constraintWidgetContainer3.verticalRun.start.resolve(y);
                            dependencyGraph2.measureWidgets();
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            measurer = measurer6;
                            ArrayList arrayList2 = dependencyGraph2.mRuns;
                            if (dimensionBehaviour7 == dimensionBehaviour9 || dimensionBehaviour8 == dimensionBehaviour9) {
                                if (z10) {
                                    Iterator it2 = arrayList2.iterator();
                                    while (true) {
                                        if (it2.hasNext()) {
                                            if (!((WidgetRun) it2.next()).supportsWrapComputation()) {
                                                z10 = false;
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                                if (z10 && dimensionBehaviour7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                                    constraintWidgetContainer3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                                    i9 = width2;
                                    constraintWidgetContainer3.setWidth(dependencyGraph2.computeWrap(constraintWidgetContainer3, 0));
                                    constraintWidgetContainer3.horizontalRun.dimension.resolve(constraintWidgetContainer3.getWidth());
                                } else {
                                    i9 = width2;
                                }
                                if (z10 && dimensionBehaviour8 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                                    constraintWidgetContainer3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                                    constraintWidgetContainer3.setHeight(dependencyGraph2.computeWrap(constraintWidgetContainer3, 1));
                                    constraintWidgetContainer3.verticalRun.dimension.resolve(constraintWidgetContainer3.getHeight());
                                }
                            } else {
                                i9 = width2;
                            }
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = constraintWidgetContainer3.mListDimensionBehaviors[0];
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour11 = ConstraintWidget.DimensionBehaviour.FIXED;
                            if (dimensionBehaviour10 == dimensionBehaviour11 || dimensionBehaviour10 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                int width3 = constraintWidgetContainer3.getWidth() + x;
                                constraintWidgetContainer3.horizontalRun.end.resolve(width3);
                                constraintWidgetContainer3.horizontalRun.dimension.resolve(width3 - x);
                                dependencyGraph2.measureWidgets();
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour12 = constraintWidgetContainer3.mListDimensionBehaviors[1];
                                if (dimensionBehaviour12 == dimensionBehaviour11 || dimensionBehaviour12 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                    int height2 = constraintWidgetContainer3.getHeight() + y;
                                    constraintWidgetContainer3.verticalRun.end.resolve(height2);
                                    constraintWidgetContainer3.verticalRun.dimension.resolve(height2 - y);
                                }
                                dependencyGraph2.measureWidgets();
                                z7 = true;
                            } else {
                                z7 = false;
                            }
                            Iterator it3 = arrayList2.iterator();
                            while (it3.hasNext()) {
                                WidgetRun widgetRun = (WidgetRun) it3.next();
                                if (widgetRun.widget != constraintWidgetContainer3 || widgetRun.resolved) {
                                    widgetRun.applyToWidget();
                                }
                            }
                            Iterator it4 = arrayList2.iterator();
                            while (it4.hasNext()) {
                                WidgetRun widgetRun2 = (WidgetRun) it4.next();
                                if (z7 || widgetRun2.widget != constraintWidgetContainer3) {
                                    if (!widgetRun2.start.resolved || ((!widgetRun2.end.resolved && !(widgetRun2 instanceof GuidelineReference)) || (!widgetRun2.dimension.resolved && !(widgetRun2 instanceof ChainRun) && !(widgetRun2 instanceof GuidelineReference)))) {
                                        z8 = false;
                                        break;
                                    }
                                }
                            }
                            z8 = true;
                            constraintWidgetContainer3.setHorizontalDimensionBehaviour(dimensionBehaviour7);
                            constraintWidgetContainer3.setVerticalDimensionBehaviour(dimensionBehaviour8);
                            z4 = z8;
                            i17 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                            i10 = 2;
                        } else {
                            z3 = z2;
                            measurer = measurer6;
                            i9 = width2;
                            boolean z12 = dependencyGraph2.mNeedBuildGraph;
                            ConstraintWidgetContainer constraintWidgetContainer4 = dependencyGraph2.container;
                            if (z12) {
                                Iterator it5 = constraintWidgetContainer4.mChildren.iterator();
                                while (it5.hasNext()) {
                                    ConstraintWidget constraintWidget3 = (ConstraintWidget) it5.next();
                                    constraintWidget3.ensureWidgetRuns();
                                    constraintWidget3.measured = false;
                                    HorizontalWidgetRun horizontalWidgetRun2 = constraintWidget3.horizontalRun;
                                    horizontalWidgetRun2.dimension.resolved = false;
                                    horizontalWidgetRun2.resolved = false;
                                    horizontalWidgetRun2.reset();
                                    VerticalWidgetRun verticalWidgetRun2 = constraintWidget3.verticalRun;
                                    verticalWidgetRun2.dimension.resolved = false;
                                    verticalWidgetRun2.resolved = false;
                                    verticalWidgetRun2.reset();
                                }
                                i16 = 0;
                                constraintWidgetContainer4.ensureWidgetRuns();
                                constraintWidgetContainer4.measured = false;
                                HorizontalWidgetRun horizontalWidgetRun3 = constraintWidgetContainer4.horizontalRun;
                                horizontalWidgetRun3.dimension.resolved = false;
                                horizontalWidgetRun3.resolved = false;
                                horizontalWidgetRun3.reset();
                                VerticalWidgetRun verticalWidgetRun3 = constraintWidgetContainer4.verticalRun;
                                verticalWidgetRun3.dimension.resolved = false;
                                verticalWidgetRun3.resolved = false;
                                verticalWidgetRun3.reset();
                                dependencyGraph2.buildGraph();
                            } else {
                                i16 = 0;
                            }
                            dependencyGraph2.basicMeasureWidgets(dependencyGraph2.mContainer);
                            constraintWidgetContainer4.f15mX = i16;
                            constraintWidgetContainer4.f16mY = i16;
                            constraintWidgetContainer4.horizontalRun.start.resolve(i16);
                            constraintWidgetContainer4.verticalRun.start.resolve(i16);
                            i17 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                            if (mode == 1073741824) {
                                i18 = 1;
                                i10 = 1;
                                z6 = constraintWidgetContainer2.directMeasureWithOrientation(i16, enabled) & true;
                            } else {
                                i18 = 1;
                                z6 = true;
                                i10 = 0;
                            }
                            if (mode2 == 1073741824) {
                                z4 = constraintWidgetContainer2.directMeasureWithOrientation(i18, enabled) & z6;
                                i10++;
                            } else {
                                z4 = z6;
                            }
                        }
                        if (z4) {
                            constraintWidgetContainer2.updateFromRuns(mode == i17, mode2 == i17);
                        }
                    } else {
                        z3 = z2;
                        measurer = measurer6;
                        i9 = width2;
                        i10 = 0;
                        z4 = false;
                    }
                    if (z4 || i10 != 2) {
                        int i27 = constraintWidgetContainer2.mOptimizationLevel;
                        if (size > 0) {
                            int size5 = constraintWidgetContainer2.mChildren.size();
                            boolean optimizeFor = constraintWidgetContainer2.optimizeFor(64);
                            BasicMeasure.Measurer measurer7 = constraintWidgetContainer2.mMeasurer;
                            for (int i28 = 0; i28 < size5; i28++) {
                                ConstraintWidget constraintWidget4 = (ConstraintWidget) constraintWidgetContainer2.mChildren.get(i28);
                                if (!(constraintWidget4 instanceof androidx.constraintlayout.core.widgets.Guideline) && !(constraintWidget4 instanceof androidx.constraintlayout.core.widgets.Barrier) && !constraintWidget4.mInVirtualLayout && (!optimizeFor || (horizontalWidgetRun = constraintWidget4.horizontalRun) == null || (verticalWidgetRun = constraintWidget4.verticalRun) == null || !horizontalWidgetRun.dimension.resolved || !verticalWidgetRun.dimension.resolved)) {
                                    ConstraintWidget.DimensionBehaviour dimensionBehaviour13 = constraintWidget4.getDimensionBehaviour(0);
                                    ConstraintWidget.DimensionBehaviour dimensionBehaviour14 = constraintWidget4.getDimensionBehaviour(1);
                                    ConstraintWidget.DimensionBehaviour dimensionBehaviour15 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                                    boolean z13 = dimensionBehaviour13 == dimensionBehaviour15 && constraintWidget4.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour14 == dimensionBehaviour15 && constraintWidget4.mMatchConstraintDefaultHeight != 1;
                                    if (!z13 && constraintWidgetContainer2.optimizeFor(1) && !(constraintWidget4 instanceof androidx.constraintlayout.core.widgets.VirtualLayout)) {
                                        if (dimensionBehaviour13 == dimensionBehaviour15 && constraintWidget4.mMatchConstraintDefaultWidth == 0 && dimensionBehaviour14 != dimensionBehaviour15 && !constraintWidget4.isInHorizontalChain()) {
                                            z13 = true;
                                        }
                                        if (dimensionBehaviour14 == dimensionBehaviour15 && constraintWidget4.mMatchConstraintDefaultHeight == 0 && dimensionBehaviour13 != dimensionBehaviour15 && !constraintWidget4.isInHorizontalChain()) {
                                            z13 = true;
                                        }
                                        if (dimensionBehaviour13 == dimensionBehaviour15 || dimensionBehaviour14 == dimensionBehaviour15) {
                                            if (constraintWidget4.mDimensionRatio > 0.0f) {
                                                z13 = true;
                                            }
                                            if (z13) {
                                                basicMeasure.measure(0, constraintWidget4, measurer7);
                                            }
                                        }
                                    }
                                    if (z13) {
                                    }
                                }
                            }
                            ConstraintLayout constraintLayout = ((Measurer) measurer7).layout;
                            int childCount2 = constraintLayout.getChildCount();
                            for (int i29 = 0; i29 < childCount2; i29++) {
                                View childAt = constraintLayout.getChildAt(i29);
                                if (childAt instanceof Placeholder) {
                                    Placeholder placeholder = (Placeholder) childAt;
                                    if (placeholder.mContent != null) {
                                        LayoutParams layoutParams = (LayoutParams) placeholder.getLayoutParams();
                                        LayoutParams layoutParams2 = (LayoutParams) placeholder.mContent.getLayoutParams();
                                        ConstraintWidget constraintWidget5 = layoutParams2.widget;
                                        constraintWidget5.mVisibility = 0;
                                        ConstraintWidget constraintWidget6 = layoutParams.widget;
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour16 = constraintWidget6.mListDimensionBehaviors[0];
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour17 = ConstraintWidget.DimensionBehaviour.FIXED;
                                        if (dimensionBehaviour16 != dimensionBehaviour17) {
                                            constraintWidget6.setWidth(constraintWidget5.getWidth());
                                        }
                                        ConstraintWidget constraintWidget7 = layoutParams.widget;
                                        if (constraintWidget7.mListDimensionBehaviors[1] != dimensionBehaviour17) {
                                            constraintWidget7.setHeight(layoutParams2.widget.getHeight());
                                        }
                                        layoutParams2.widget.mVisibility = 8;
                                    }
                                }
                            }
                            int size6 = constraintLayout.mConstraintHelpers.size();
                            if (size6 > 0) {
                                for (int i30 = 0; i30 < size6; i30++) {
                                    ((ConstraintHelper) constraintLayout.mConstraintHelpers.get(i30)).getClass();
                                }
                            }
                        }
                        basicMeasure.updateHierarchy(constraintWidgetContainer2);
                        ?? r4 = basicMeasure.mVariableDimensionsWidgets;
                        size2 = r4.size();
                        if (size > 0) {
                            i11 = i9;
                            r13 = 0;
                            basicMeasure.solveLinearSystem(constraintWidgetContainer2, 0, i11, height);
                        } else {
                            i11 = i9;
                            r13 = 0;
                        }
                        if (size2 > 0) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer2.mListDimensionBehaviors;
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour18 = dimensionBehaviourArr2[r13];
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour19 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            char c = dimensionBehaviour18 == dimensionBehaviour19 ? (char) 1 : r13;
                            char c2 = dimensionBehaviourArr2[1] == dimensionBehaviour19 ? (char) 1 : r13;
                            int width4 = constraintWidgetContainer.getWidth();
                            ConstraintWidgetContainer constraintWidgetContainer5 = basicMeasure.constraintWidgetContainer;
                            int max9 = Math.max(width4, constraintWidgetContainer5.mMinWidth);
                            int max10 = Math.max(constraintWidgetContainer.getHeight(), constraintWidgetContainer5.mMinHeight);
                            boolean z14 = r13;
                            boolean z15 = z14;
                            for (?? r12 = z14; r12 < size2; r12++) {
                                ConstraintWidget constraintWidget8 = (ConstraintWidget) r4.get(r12);
                                if (constraintWidget8 instanceof androidx.constraintlayout.core.widgets.VirtualLayout) {
                                    int width5 = constraintWidget8.getWidth();
                                    int height3 = constraintWidget8.getHeight();
                                    i14 = i27;
                                    measurer3 = measurer;
                                    boolean measure = z15 | basicMeasure.measure(1, constraintWidget8, measurer3);
                                    int width6 = constraintWidget8.getWidth();
                                    int height4 = constraintWidget8.getHeight();
                                    if (width6 != width5) {
                                        constraintWidget8.setWidth(width6);
                                        if (c != 0 && constraintWidget8.getX() + constraintWidget8.mWidth > max9) {
                                            max9 = Math.max(max9, constraintWidget8.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin() + constraintWidget8.getX() + constraintWidget8.mWidth);
                                        }
                                        i15 = max9;
                                        z5 = true;
                                    } else {
                                        i15 = max9;
                                        z5 = measure;
                                    }
                                    if (height4 != height3) {
                                        constraintWidget8.setHeight(height4);
                                        if (c2 != 0 && constraintWidget8.getY() + constraintWidget8.mHeight > max10) {
                                            max10 = Math.max(max10, constraintWidget8.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin() + constraintWidget8.getY() + constraintWidget8.mHeight);
                                        }
                                        z5 = true;
                                    }
                                    z15 = ((androidx.constraintlayout.core.widgets.VirtualLayout) constraintWidget8).mNeedsCallFromSolver | z5;
                                    max9 = i15;
                                } else {
                                    i14 = i27;
                                    measurer3 = measurer;
                                }
                                measurer = measurer3;
                                i27 = i14;
                            }
                            i12 = i27;
                            BasicMeasure.Measurer measurer8 = measurer;
                            int i31 = 2;
                            int i32 = 0;
                            ArrayList arrayList3 = r4;
                            while (i32 < i31) {
                                int i33 = 0;
                                ArrayList arrayList4 = arrayList3;
                                while (i33 < size2) {
                                    ConstraintWidget constraintWidget9 = (ConstraintWidget) arrayList4.get(i33);
                                    if ((!(constraintWidget9 instanceof Helper) || (constraintWidget9 instanceof androidx.constraintlayout.core.widgets.VirtualLayout)) && !(constraintWidget9 instanceof androidx.constraintlayout.core.widgets.Guideline)) {
                                        if (constraintWidget9.mVisibility != 8 && ((!z3 || !constraintWidget9.horizontalRun.dimension.resolved || !constraintWidget9.verticalRun.dimension.resolved) && !(constraintWidget9 instanceof androidx.constraintlayout.core.widgets.VirtualLayout))) {
                                            int width7 = constraintWidget9.getWidth();
                                            int height5 = constraintWidget9.getHeight();
                                            arrayList = arrayList4;
                                            int i34 = constraintWidget9.mBaselineDistance;
                                            i13 = size2;
                                            boolean measure2 = basicMeasure.measure(i32 == 1 ? 2 : 1, constraintWidget9, measurer8) | z15;
                                            int width8 = constraintWidget9.getWidth();
                                            measurer2 = measurer8;
                                            int height6 = constraintWidget9.getHeight();
                                            if (width8 != width7) {
                                                constraintWidget9.setWidth(width8);
                                                if (c != 0 && constraintWidget9.getX() + constraintWidget9.mWidth > max9) {
                                                    max9 = Math.max(max9, constraintWidget9.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin() + constraintWidget9.getX() + constraintWidget9.mWidth);
                                                }
                                                measure2 = true;
                                            }
                                            if (height6 != height5) {
                                                constraintWidget9.setHeight(height6);
                                                if (c2 != 0 && constraintWidget9.getY() + constraintWidget9.mHeight > max10) {
                                                    max10 = Math.max(max10, constraintWidget9.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin() + constraintWidget9.getY() + constraintWidget9.mHeight);
                                                }
                                                measure2 = true;
                                            }
                                            z15 = (!constraintWidget9.hasBaseline || i34 == constraintWidget9.mBaselineDistance) ? measure2 : true;
                                            i33++;
                                            size2 = i13;
                                            arrayList4 = arrayList;
                                            measurer8 = measurer2;
                                        }
                                    }
                                    measurer2 = measurer8;
                                    arrayList = arrayList4;
                                    i13 = size2;
                                    i33++;
                                    size2 = i13;
                                    arrayList4 = arrayList;
                                    measurer8 = measurer2;
                                }
                                BasicMeasure.Measurer measurer9 = measurer8;
                                ArrayList arrayList5 = arrayList4;
                                int i35 = size2;
                                if (!z15) {
                                    break;
                                }
                                i32++;
                                basicMeasure.solveLinearSystem(constraintWidgetContainer, i32, i11, height);
                                size2 = i35;
                                arrayList3 = arrayList5;
                                measurer8 = measurer9;
                                i31 = 2;
                                z15 = false;
                            }
                            constraintWidgetContainer2 = constraintWidgetContainer;
                        } else {
                            i12 = i27;
                        }
                        constraintWidgetContainer2.mOptimizationLevel = i12;
                        LinearSystem.USE_DEPENDENCY_ORDERING = constraintWidgetContainer2.optimizeFor(512);
                    }
                    return;
                }
                dimensionBehaviour = dimensionBehaviour3;
            }
            dimensionBehaviour2 = dimensionBehaviour;
            i5 = 0;
        } else {
            dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (childCount != 0) {
                dimensionBehaviour2 = dimensionBehaviour;
                i4 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                i5 = i21;
                if (mode2 == i4) {
                }
                width = constraintWidgetContainer.getWidth();
                dependencyGraph = constraintWidgetContainer2.mDependencyGraph;
                if (i5 == width) {
                }
                dependencyGraph.mNeedRedoMeasures = true;
                constraintWidgetContainer2.f15mX = 0;
                constraintWidgetContainer2.f16mY = 0;
                int i252 = this.mMaxWidth - i24;
                int[] iArr2 = constraintWidgetContainer2.mMaxDimension;
                iArr2[0] = i252;
                iArr2[1] = this.mMaxHeight - i23;
                constraintWidgetContainer2.mMinWidth = 0;
                constraintWidgetContainer2.mMinHeight = 0;
                constraintWidgetContainer2.setHorizontalDimensionBehaviour(dimensionBehaviour2);
                constraintWidgetContainer2.setWidth(i5);
                constraintWidgetContainer2.setVerticalDimensionBehaviour(dimensionBehaviour3);
                constraintWidgetContainer2.setHeight(max2);
                i6 = this.mMinWidth - i24;
                if (i6 < 0) {
                }
                i8 = this.mMinHeight - i23;
                if (i8 < 0) {
                }
                constraintWidgetContainer2.mPaddingLeft = max7;
                constraintWidgetContainer2.mPaddingTop = max3;
                basicMeasure = constraintWidgetContainer2.mBasicMeasureSolver;
                basicMeasure.getClass();
                BasicMeasure.Measurer measurer62 = constraintWidgetContainer2.mMeasurer;
                size = constraintWidgetContainer2.mChildren.size();
                int width22 = constraintWidgetContainer.getWidth();
                int height7 = constraintWidgetContainer.getHeight();
                boolean enabled2 = Optimizer.enabled(i, 128);
                if (enabled2) {
                }
                if (z) {
                }
                z2 = z & ((mode != 1073741824 && mode2 == 1073741824) || enabled2);
                if (z2) {
                }
                if (z4) {
                }
                int i272 = constraintWidgetContainer2.mOptimizationLevel;
                if (size > 0) {
                }
                basicMeasure.updateHierarchy(constraintWidgetContainer2);
                ?? r42 = basicMeasure.mVariableDimensionsWidgets;
                size2 = r42.size();
                if (size > 0) {
                }
                if (size2 > 0) {
                }
                constraintWidgetContainer2.mOptimizationLevel = i12;
                LinearSystem.USE_DEPENDENCY_ORDERING = constraintWidgetContainer2.optimizeFor(512);
            }
            max = Math.max(0, this.mMinWidth);
            ConstraintWidget.DimensionBehaviour dimensionBehaviour42 = dimensionBehaviour;
            i5 = max;
            dimensionBehaviour2 = dimensionBehaviour42;
        }
        i4 = VideoPlayer.MEDIA_ERROR_SYSTEM;
        if (mode2 == i4) {
        }
        width = constraintWidgetContainer.getWidth();
        dependencyGraph = constraintWidgetContainer2.mDependencyGraph;
        if (i5 == width) {
        }
        dependencyGraph.mNeedRedoMeasures = true;
        constraintWidgetContainer2.f15mX = 0;
        constraintWidgetContainer2.f16mY = 0;
        int i2522 = this.mMaxWidth - i24;
        int[] iArr22 = constraintWidgetContainer2.mMaxDimension;
        iArr22[0] = i2522;
        iArr22[1] = this.mMaxHeight - i23;
        constraintWidgetContainer2.mMinWidth = 0;
        constraintWidgetContainer2.mMinHeight = 0;
        constraintWidgetContainer2.setHorizontalDimensionBehaviour(dimensionBehaviour2);
        constraintWidgetContainer2.setWidth(i5);
        constraintWidgetContainer2.setVerticalDimensionBehaviour(dimensionBehaviour3);
        constraintWidgetContainer2.setHeight(max2);
        i6 = this.mMinWidth - i24;
        if (i6 < 0) {
        }
        i8 = this.mMinHeight - i23;
        if (i8 < 0) {
        }
        constraintWidgetContainer2.mPaddingLeft = max7;
        constraintWidgetContainer2.mPaddingTop = max3;
        basicMeasure = constraintWidgetContainer2.mBasicMeasureSolver;
        basicMeasure.getClass();
        BasicMeasure.Measurer measurer622 = constraintWidgetContainer2.mMeasurer;
        size = constraintWidgetContainer2.mChildren.size();
        int width222 = constraintWidgetContainer.getWidth();
        int height72 = constraintWidgetContainer.getHeight();
        boolean enabled22 = Optimizer.enabled(i, 128);
        if (enabled22) {
        }
        if (z) {
        }
        z2 = z & ((mode != 1073741824 && mode2 == 1073741824) || enabled22);
        if (z2) {
        }
        if (z4) {
        }
        int i2722 = constraintWidgetContainer2.mOptimizationLevel;
        if (size > 0) {
        }
        basicMeasure.updateHierarchy(constraintWidgetContainer2);
        ?? r422 = basicMeasure.mVariableDimensionsWidgets;
        size2 = r422.size();
        if (size > 0) {
        }
        if (size2 > 0) {
        }
        constraintWidgetContainer2.mOptimizationLevel = i12;
        LinearSystem.USE_DEPENDENCY_ORDERING = constraintWidgetContainer2.optimizeFor(512);
    }

    public final void setDesignInformation(Object obj, Object obj2) {
        if ((obj instanceof String) && (obj2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap();
            }
            String str = (String) obj;
            int indexOf = str.indexOf("/");
            if (indexOf != -1) {
                str = str.substring(indexOf + 1);
            }
            this.mDesignIds.put(str, Integer.valueOf(((Integer) obj2).intValue()));
        }
    }

    @Override // android.view.View
    public final void setId(int i) {
        this.mChildrenByIds.remove(getId());
        super.setId(i);
        this.mChildrenByIds.put(getId(), this);
    }

    public final void setWidgetBaseline(ConstraintWidget constraintWidget, LayoutParams layoutParams, SparseArray sparseArray, int i, ConstraintAnchor.Type type) {
        View view = (View) this.mChildrenByIds.get(i);
        ConstraintWidget constraintWidget2 = (ConstraintWidget) sparseArray.get(i);
        if (constraintWidget2 == null || view == null || !(view.getLayoutParams() instanceof LayoutParams)) {
            return;
        }
        layoutParams.needsBaseline = true;
        ConstraintAnchor.Type type2 = ConstraintAnchor.Type.BASELINE;
        if (type == type2) {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.needsBaseline = true;
            layoutParams2.widget.hasBaseline = true;
        }
        constraintWidget.getAnchor(type2).connect(constraintWidget2.getAnchor(type), layoutParams.baselineMargin, layoutParams.goneBaselineMargin, true);
        constraintWidget.hasBaseline = true;
        constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
        constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChildrenByIds = new SparseArray();
        this.mConstraintHelpers = new ArrayList(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap();
        this.mTempMapIdToWidget = new SparseArray();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildrenByIds = new SparseArray();
        this.mConstraintHelpers = new ArrayList(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap();
        this.mTempMapIdToWidget = new SparseArray();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, i, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChildrenByIds = new SparseArray();
        this.mConstraintHelpers = new ArrayList(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap();
        this.mTempMapIdToWidget = new SparseArray();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, i, i2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public final int baselineMargin;
        public int baselineToBaseline;
        public int baselineToBottom;
        public int baselineToTop;
        public int bottomToBottom;
        public int bottomToTop;
        public float circleAngle;
        public int circleConstraint;
        public int circleRadius;
        public boolean constrainedHeight;
        public boolean constrainedWidth;
        public String constraintTag;
        public String dimensionRatio;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public final int goneBaselineMargin;
        public int goneBottomMargin;
        public int goneEndMargin;
        public final int goneLeftMargin;
        public final int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public final boolean guidelineUseRtl;
        public float horizontalBias;
        public int horizontalChainStyle;
        public boolean horizontalDimensionFixed;
        public float horizontalWeight;
        public boolean isGuideline;
        public boolean isHelper;
        public boolean isInPlaceholder;
        public int leftToLeft;
        public int leftToRight;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        public float matchConstraintPercentHeight;
        public float matchConstraintPercentWidth;
        public boolean needsBaseline;
        public int orientation;
        public int resolveGoneLeftMargin;
        public int resolveGoneRightMargin;
        public int resolvedGuideBegin;
        public int resolvedGuideEnd;
        public float resolvedGuidePercent;
        public float resolvedHorizontalBias;
        public int resolvedLeftToLeft;
        public int resolvedLeftToRight;
        public int resolvedRightToLeft;
        public int resolvedRightToRight;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        public boolean verticalDimensionFixed;
        public float verticalWeight;
        public ConstraintWidget widget;
        public int wrapBehaviorInParent;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Table {
            public static final SparseIntArray map;

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                map = sparseIntArray;
                sparseIntArray.append(98, 64);
                sparseIntArray.append(75, 65);
                sparseIntArray.append(84, 8);
                sparseIntArray.append(85, 9);
                sparseIntArray.append(87, 10);
                sparseIntArray.append(88, 11);
                sparseIntArray.append(94, 12);
                sparseIntArray.append(93, 13);
                sparseIntArray.append(65, 14);
                sparseIntArray.append(64, 15);
                sparseIntArray.append(60, 16);
                sparseIntArray.append(62, 52);
                sparseIntArray.append(61, 53);
                sparseIntArray.append(66, 2);
                sparseIntArray.append(68, 3);
                sparseIntArray.append(67, 4);
                sparseIntArray.append(103, 49);
                sparseIntArray.append(104, 50);
                sparseIntArray.append(72, 5);
                sparseIntArray.append(73, 6);
                sparseIntArray.append(74, 7);
                sparseIntArray.append(55, 67);
                sparseIntArray.append(0, 1);
                sparseIntArray.append(89, 17);
                sparseIntArray.append(90, 18);
                sparseIntArray.append(71, 19);
                sparseIntArray.append(70, 20);
                sparseIntArray.append(108, 21);
                sparseIntArray.append(111, 22);
                sparseIntArray.append(109, 23);
                sparseIntArray.append(106, 24);
                sparseIntArray.append(110, 25);
                sparseIntArray.append(107, 26);
                sparseIntArray.append(105, 55);
                sparseIntArray.append(112, 54);
                sparseIntArray.append(80, 29);
                sparseIntArray.append(95, 30);
                sparseIntArray.append(69, 44);
                sparseIntArray.append(82, 45);
                sparseIntArray.append(97, 46);
                sparseIntArray.append(81, 47);
                sparseIntArray.append(96, 48);
                sparseIntArray.append(58, 27);
                sparseIntArray.append(57, 28);
                sparseIntArray.append(99, 31);
                sparseIntArray.append(76, 32);
                sparseIntArray.append(101, 33);
                sparseIntArray.append(100, 34);
                sparseIntArray.append(102, 35);
                sparseIntArray.append(78, 36);
                sparseIntArray.append(77, 37);
                sparseIntArray.append(79, 38);
                sparseIntArray.append(83, 39);
                sparseIntArray.append(92, 40);
                sparseIntArray.append(86, 41);
                sparseIntArray.append(63, 42);
                sparseIntArray.append(59, 43);
                sparseIntArray.append(91, 51);
                sparseIntArray.append(114, 66);
            }

            private Table() {
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneTopMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBottomMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneStartMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneEndMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBaselineMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.baselineMargin = 0;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolveGoneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.guideBegin = layoutParams.guideBegin;
            this.guideEnd = layoutParams.guideEnd;
            this.guidePercent = layoutParams.guidePercent;
            this.guidelineUseRtl = layoutParams.guidelineUseRtl;
            this.leftToLeft = layoutParams.leftToLeft;
            this.leftToRight = layoutParams.leftToRight;
            this.rightToLeft = layoutParams.rightToLeft;
            this.rightToRight = layoutParams.rightToRight;
            this.topToTop = layoutParams.topToTop;
            this.topToBottom = layoutParams.topToBottom;
            this.bottomToTop = layoutParams.bottomToTop;
            this.bottomToBottom = layoutParams.bottomToBottom;
            this.baselineToBaseline = layoutParams.baselineToBaseline;
            this.baselineToTop = layoutParams.baselineToTop;
            this.baselineToBottom = layoutParams.baselineToBottom;
            this.circleConstraint = layoutParams.circleConstraint;
            this.circleRadius = layoutParams.circleRadius;
            this.circleAngle = layoutParams.circleAngle;
            this.startToEnd = layoutParams.startToEnd;
            this.startToStart = layoutParams.startToStart;
            this.endToStart = layoutParams.endToStart;
            this.endToEnd = layoutParams.endToEnd;
            this.goneLeftMargin = layoutParams.goneLeftMargin;
            this.goneTopMargin = layoutParams.goneTopMargin;
            this.goneRightMargin = layoutParams.goneRightMargin;
            this.goneBottomMargin = layoutParams.goneBottomMargin;
            this.goneStartMargin = layoutParams.goneStartMargin;
            this.goneEndMargin = layoutParams.goneEndMargin;
            this.goneBaselineMargin = layoutParams.goneBaselineMargin;
            this.baselineMargin = layoutParams.baselineMargin;
            this.horizontalBias = layoutParams.horizontalBias;
            this.verticalBias = layoutParams.verticalBias;
            this.dimensionRatio = layoutParams.dimensionRatio;
            this.horizontalWeight = layoutParams.horizontalWeight;
            this.verticalWeight = layoutParams.verticalWeight;
            this.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.verticalChainStyle = layoutParams.verticalChainStyle;
            this.constrainedWidth = layoutParams.constrainedWidth;
            this.constrainedHeight = layoutParams.constrainedHeight;
            this.matchConstraintDefaultWidth = layoutParams.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = layoutParams.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = layoutParams.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = layoutParams.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = layoutParams.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = layoutParams.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = layoutParams.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = layoutParams.matchConstraintPercentHeight;
            this.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.orientation = layoutParams.orientation;
            this.horizontalDimensionFixed = layoutParams.horizontalDimensionFixed;
            this.verticalDimensionFixed = layoutParams.verticalDimensionFixed;
            this.needsBaseline = layoutParams.needsBaseline;
            this.isGuideline = layoutParams.isGuideline;
            this.resolvedLeftToLeft = layoutParams.resolvedLeftToLeft;
            this.resolvedLeftToRight = layoutParams.resolvedLeftToRight;
            this.resolvedRightToLeft = layoutParams.resolvedRightToLeft;
            this.resolvedRightToRight = layoutParams.resolvedRightToRight;
            this.resolveGoneLeftMargin = layoutParams.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = layoutParams.resolveGoneRightMargin;
            this.resolvedHorizontalBias = layoutParams.resolvedHorizontalBias;
            this.constraintTag = layoutParams.constraintTag;
            this.wrapBehaviorInParent = layoutParams.wrapBehaviorInParent;
            this.widget = layoutParams.widget;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x004a  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0051  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0082  */
        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void resolveLayoutDirection(int i) {
            int i2;
            int i3;
            int i4;
            int i5;
            int i6 = ((ViewGroup.MarginLayoutParams) this).leftMargin;
            int i7 = ((ViewGroup.MarginLayoutParams) this).rightMargin;
            super.resolveLayoutDirection(i);
            boolean z = false;
            boolean z2 = 1 == getLayoutDirection();
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            float f = this.horizontalBias;
            this.resolvedHorizontalBias = f;
            int i8 = this.guideBegin;
            this.resolvedGuideBegin = i8;
            int i9 = this.guideEnd;
            this.resolvedGuideEnd = i9;
            float f2 = this.guidePercent;
            this.resolvedGuidePercent = f2;
            if (z2) {
                int i10 = this.startToEnd;
                if (i10 != -1) {
                    this.resolvedRightToLeft = i10;
                } else {
                    int i11 = this.startToStart;
                    if (i11 != -1) {
                        this.resolvedRightToRight = i11;
                    }
                    i2 = this.endToStart;
                    if (i2 != -1) {
                        this.resolvedLeftToRight = i2;
                        z = true;
                    }
                    i3 = this.endToEnd;
                    if (i3 != -1) {
                        this.resolvedLeftToLeft = i3;
                        z = true;
                    }
                    i4 = this.goneStartMargin;
                    if (i4 != Integer.MIN_VALUE) {
                        this.resolveGoneRightMargin = i4;
                    }
                    i5 = this.goneEndMargin;
                    if (i5 != Integer.MIN_VALUE) {
                        this.resolveGoneLeftMargin = i5;
                    }
                    if (z) {
                        this.resolvedHorizontalBias = 1.0f - f;
                    }
                    if (this.isGuideline && this.orientation == 1 && this.guidelineUseRtl) {
                        if (f2 == -1.0f) {
                            this.resolvedGuidePercent = 1.0f - f2;
                            this.resolvedGuideBegin = -1;
                            this.resolvedGuideEnd = -1;
                        } else if (i8 != -1) {
                            this.resolvedGuideEnd = i8;
                            this.resolvedGuideBegin = -1;
                            this.resolvedGuidePercent = -1.0f;
                        } else if (i9 != -1) {
                            this.resolvedGuideBegin = i9;
                            this.resolvedGuideEnd = -1;
                            this.resolvedGuidePercent = -1.0f;
                        }
                    }
                }
                z = true;
                i2 = this.endToStart;
                if (i2 != -1) {
                }
                i3 = this.endToEnd;
                if (i3 != -1) {
                }
                i4 = this.goneStartMargin;
                if (i4 != Integer.MIN_VALUE) {
                }
                i5 = this.goneEndMargin;
                if (i5 != Integer.MIN_VALUE) {
                }
                if (z) {
                }
                if (this.isGuideline) {
                    if (f2 == -1.0f) {
                    }
                }
            } else {
                int i12 = this.startToEnd;
                if (i12 != -1) {
                    this.resolvedLeftToRight = i12;
                }
                int i13 = this.startToStart;
                if (i13 != -1) {
                    this.resolvedLeftToLeft = i13;
                }
                int i14 = this.endToStart;
                if (i14 != -1) {
                    this.resolvedRightToLeft = i14;
                }
                int i15 = this.endToEnd;
                if (i15 != -1) {
                    this.resolvedRightToRight = i15;
                }
                int i16 = this.goneStartMargin;
                if (i16 != Integer.MIN_VALUE) {
                    this.resolveGoneLeftMargin = i16;
                }
                int i17 = this.goneEndMargin;
                if (i17 != Integer.MIN_VALUE) {
                    this.resolveGoneRightMargin = i17;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
                int i18 = this.rightToLeft;
                if (i18 != -1) {
                    this.resolvedRightToLeft = i18;
                    if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                        ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                    }
                } else {
                    int i19 = this.rightToRight;
                    if (i19 != -1) {
                        this.resolvedRightToRight = i19;
                        if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                            ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                        }
                    }
                }
                int i20 = this.leftToLeft;
                if (i20 != -1) {
                    this.resolvedLeftToLeft = i20;
                    if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                    return;
                }
                int i21 = this.leftToRight;
                if (i21 != -1) {
                    this.resolvedLeftToRight = i21;
                    if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                }
            }
        }

        public final void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            int i = ((ViewGroup.MarginLayoutParams) this).width;
            if (i == -2 && this.constrainedWidth) {
                this.horizontalDimensionFixed = false;
                if (this.matchConstraintDefaultWidth == 0) {
                    this.matchConstraintDefaultWidth = 1;
                }
            }
            int i2 = ((ViewGroup.MarginLayoutParams) this).height;
            if (i2 == -2 && this.constrainedHeight) {
                this.verticalDimensionFixed = false;
                if (this.matchConstraintDefaultHeight == 0) {
                    this.matchConstraintDefaultHeight = 1;
                }
            }
            if (i == 0 || i == -1) {
                this.horizontalDimensionFixed = false;
                if (i == 0 && this.matchConstraintDefaultWidth == 1) {
                    ((ViewGroup.MarginLayoutParams) this).width = -2;
                    this.constrainedWidth = true;
                }
            }
            if (i2 == 0 || i2 == -1) {
                this.verticalDimensionFixed = false;
                if (i2 == 0 && this.matchConstraintDefaultHeight == 1) {
                    ((ViewGroup.MarginLayoutParams) this).height = -2;
                    this.constrainedHeight = true;
                }
            }
            if (this.guidePercent == -1.0f && this.guideBegin == -1 && this.guideEnd == -1) {
                return;
            }
            this.isGuideline = true;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            if (!(this.widget instanceof androidx.constraintlayout.core.widgets.Guideline)) {
                this.widget = new androidx.constraintlayout.core.widgets.Guideline();
            }
            ((androidx.constraintlayout.core.widgets.Guideline) this.widget).setOrientation(this.orientation);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneTopMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBottomMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneStartMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneEndMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBaselineMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.baselineMargin = 0;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolveGoneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                int i2 = Table.map.get(index);
                switch (i2) {
                    case 1:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 2:
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.circleConstraint);
                        this.circleConstraint = resourceId;
                        if (resourceId == -1) {
                            this.circleConstraint = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                        break;
                    case 4:
                        float f = obtainStyledAttributes.getFloat(index, this.circleAngle) % 360.0f;
                        this.circleAngle = f;
                        if (f < 0.0f) {
                            this.circleAngle = (360.0f - f) % 360.0f;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 6:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 7:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 8:
                        int resourceId2 = obtainStyledAttributes.getResourceId(index, this.leftToLeft);
                        this.leftToLeft = resourceId2;
                        if (resourceId2 == -1) {
                            this.leftToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        int resourceId3 = obtainStyledAttributes.getResourceId(index, this.leftToRight);
                        this.leftToRight = resourceId3;
                        if (resourceId3 == -1) {
                            this.leftToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        int resourceId4 = obtainStyledAttributes.getResourceId(index, this.rightToLeft);
                        this.rightToLeft = resourceId4;
                        if (resourceId4 == -1) {
                            this.rightToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        int resourceId5 = obtainStyledAttributes.getResourceId(index, this.rightToRight);
                        this.rightToRight = resourceId5;
                        if (resourceId5 == -1) {
                            this.rightToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        int resourceId6 = obtainStyledAttributes.getResourceId(index, this.topToTop);
                        this.topToTop = resourceId6;
                        if (resourceId6 == -1) {
                            this.topToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        int resourceId7 = obtainStyledAttributes.getResourceId(index, this.topToBottom);
                        this.topToBottom = resourceId7;
                        if (resourceId7 == -1) {
                            this.topToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        int resourceId8 = obtainStyledAttributes.getResourceId(index, this.bottomToTop);
                        this.bottomToTop = resourceId8;
                        if (resourceId8 == -1) {
                            this.bottomToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        int resourceId9 = obtainStyledAttributes.getResourceId(index, this.bottomToBottom);
                        this.bottomToBottom = resourceId9;
                        if (resourceId9 == -1) {
                            this.bottomToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        int resourceId10 = obtainStyledAttributes.getResourceId(index, this.baselineToBaseline);
                        this.baselineToBaseline = resourceId10;
                        if (resourceId10 == -1) {
                            this.baselineToBaseline = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        int resourceId11 = obtainStyledAttributes.getResourceId(index, this.startToEnd);
                        this.startToEnd = resourceId11;
                        if (resourceId11 == -1) {
                            this.startToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        int resourceId12 = obtainStyledAttributes.getResourceId(index, this.startToStart);
                        this.startToStart = resourceId12;
                        if (resourceId12 == -1) {
                            this.startToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 19:
                        int resourceId13 = obtainStyledAttributes.getResourceId(index, this.endToStart);
                        this.endToStart = resourceId13;
                        if (resourceId13 == -1) {
                            this.endToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 20:
                        int resourceId14 = obtainStyledAttributes.getResourceId(index, this.endToEnd);
                        this.endToEnd = resourceId14;
                        if (resourceId14 == -1) {
                            this.endToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 21:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 22:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 23:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 24:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 25:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 26:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 27:
                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                        break;
                    case 28:
                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                        break;
                    case 29:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 30:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 31:
                        int i3 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultWidth = i3;
                        if (i3 == 1) {
                            Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        } else {
                            break;
                        }
                    case 32:
                        int i4 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultHeight = i4;
                        if (i4 == 1) {
                            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        } else {
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinWidth);
                            break;
                        } catch (Exception unused) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinWidth) == -2) {
                                this.matchConstraintMinWidth = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxWidth);
                            break;
                        } catch (Exception unused2) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxWidth) == -2) {
                                this.matchConstraintMaxWidth = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentWidth));
                        this.matchConstraintDefaultWidth = 2;
                        break;
                    case 36:
                        try {
                            this.matchConstraintMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinHeight);
                            break;
                        } catch (Exception unused3) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinHeight) == -2) {
                                this.matchConstraintMinHeight = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxHeight);
                            break;
                        } catch (Exception unused4) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxHeight) == -2) {
                                this.matchConstraintMaxHeight = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentHeight));
                        this.matchConstraintDefaultHeight = 2;
                        break;
                    default:
                        switch (i2) {
                            case 44:
                                ConstraintSet.parseDimensionRatioString(this, obtainStyledAttributes.getString(index));
                                break;
                            case 45:
                                this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                                break;
                            case 46:
                                this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                                break;
                            case 47:
                                this.horizontalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 48:
                                this.verticalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 49:
                                this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                                break;
                            case 50:
                                this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                                break;
                            case 51:
                                this.constraintTag = obtainStyledAttributes.getString(index);
                                break;
                            case 52:
                                int resourceId15 = obtainStyledAttributes.getResourceId(index, this.baselineToTop);
                                this.baselineToTop = resourceId15;
                                if (resourceId15 == -1) {
                                    this.baselineToTop = obtainStyledAttributes.getInt(index, -1);
                                    break;
                                } else {
                                    break;
                                }
                            case 53:
                                int resourceId16 = obtainStyledAttributes.getResourceId(index, this.baselineToBottom);
                                this.baselineToBottom = resourceId16;
                                if (resourceId16 == -1) {
                                    this.baselineToBottom = obtainStyledAttributes.getInt(index, -1);
                                    break;
                                } else {
                                    break;
                                }
                            case 54:
                                this.baselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.baselineMargin);
                                break;
                            case 55:
                                this.goneBaselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBaselineMargin);
                                break;
                            default:
                                switch (i2) {
                                    case 64:
                                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 0);
                                        break;
                                    case 65:
                                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 1);
                                        break;
                                    case 66:
                                        this.wrapBehaviorInParent = obtainStyledAttributes.getInt(index, this.wrapBehaviorInParent);
                                        break;
                                    case 67:
                                        this.guidelineUseRtl = obtainStyledAttributes.getBoolean(index, this.guidelineUseRtl);
                                        break;
                                }
                        }
                }
            }
            obtainStyledAttributes.recycle();
            validate();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneTopMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBottomMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneStartMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneEndMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBaselineMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.baselineMargin = 0;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolveGoneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneTopMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBottomMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneStartMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneEndMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.goneBaselineMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.baselineMargin = 0;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolveGoneRightMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }
    }
}
