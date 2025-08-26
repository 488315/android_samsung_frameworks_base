package androidx.constraintlayout.core.widgets;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConstraintWidget {
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public final boolean[] isTerminalWidget;
    public final ArrayList mAnchors;
    public boolean mAnimated;
    public final ConstraintAnchor mBaseline;
    public int mBaselineDistance;
    public final ConstraintAnchor mBottom;
    public final ConstraintAnchor mCenter;
    public final ConstraintAnchor mCenterX;
    public final ConstraintAnchor mCenterY;
    public float mCircleConstraintAngle;
    public Object mCompanionWidget;
    public String mDebugName;
    public float mDimensionRatio;
    public int mDimensionRatioSide;
    public boolean mHasBaseline;
    public int mHeight;
    public int mHeightOverride;
    public float mHorizontalBiasPercent;
    public int mHorizontalChainStyle;
    public ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    public HorizontalWidgetRun mHorizontalRun;
    public boolean mHorizontalSolvingPass;
    public boolean mInPlaceholder;
    public boolean mInVirtualLayout;
    public final boolean[] mIsInBarrier;
    public int mLastHorizontalMeasureSpec;
    public int mLastVerticalMeasureSpec;
    public final ConstraintAnchor mLeft;
    public final ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    public final ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    public int[] mMaxDimension;
    public boolean mMeasureRequested;
    public int mMinHeight;
    public int mMinWidth;
    public final ConstraintWidget[] mNextChainWidget;
    public final boolean mOptimizeWrapOnResolved;
    public ConstraintWidget mParent;
    public float mResolvedDimensionRatio;
    public int mResolvedDimensionRatioSide;
    public boolean mResolvedHorizontal;
    public final int[] mResolvedMatchConstraintDefault;
    public boolean mResolvedVertical;
    public final ConstraintAnchor mRight;
    public final ConstraintAnchor mTop;
    public float mVerticalBiasPercent;
    public int mVerticalChainStyle;
    public ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    public VerticalWidgetRun mVerticalRun;
    public boolean mVerticalSolvingPass;
    public int mVisibility;
    public final float[] mWeight;
    public int mWidth;
    public int mWidthOverride;
    public int mWrapBehaviorInParent;
    public int mX;
    public int mY;
    public boolean measured;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;

    /* renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public ConstraintWidget() {
        this.measured = false;
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mLeft = constraintAnchor;
        ConstraintAnchor constraintAnchor2 = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mTop = constraintAnchor2;
        ConstraintAnchor constraintAnchor3 = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mRight = constraintAnchor3;
        ConstraintAnchor constraintAnchor4 = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBottom = constraintAnchor4;
        ConstraintAnchor constraintAnchor5 = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mBaseline = constraintAnchor5;
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor6 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor6;
        this.mListAnchors = new ConstraintAnchor[]{constraintAnchor, constraintAnchor3, constraintAnchor2, constraintAnchor4, constraintAnchor5, constraintAnchor6};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    public static void serializeAttribute(StringBuilder sb, String str, float f, float f2) {
        if (f == f2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(f);
        sb.append(",\n");
    }

    public final void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public final void addChildrenToSolverByDependency(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, HashSet hashSet, int i, boolean z) {
        if (z) {
            if (!hashSet.contains(this)) {
                return;
            }
            Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem, this);
            hashSet.remove(this);
            addToSolver(linearSystem, constraintWidgetContainer.optimizeFor(64));
        }
        if (i == 0) {
            HashSet hashSet2 = this.mLeft.mDependents;
            if (hashSet2 != null) {
                Iterator it = hashSet2.iterator();
                while (it.hasNext()) {
                    ((ConstraintAnchor) it.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
            }
            HashSet hashSet3 = this.mRight.mDependents;
            if (hashSet3 != null) {
                Iterator it2 = hashSet3.iterator();
                while (it2.hasNext()) {
                    ((ConstraintAnchor) it2.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
                return;
            }
            return;
        }
        HashSet hashSet4 = this.mTop.mDependents;
        if (hashSet4 != null) {
            Iterator it3 = hashSet4.iterator();
            while (it3.hasNext()) {
                ((ConstraintAnchor) it3.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet hashSet5 = this.mBottom.mDependents;
        if (hashSet5 != null) {
            Iterator it4 = hashSet5.iterator();
            while (it4.hasNext()) {
                ((ConstraintAnchor) it4.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet hashSet6 = this.mBaseline.mDependents;
        if (hashSet6 != null) {
            Iterator it5 = hashSet6.iterator();
            while (it5.hasNext()) {
                ((ConstraintAnchor) it5.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x045e  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x046b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0481  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x048a  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x04a4  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x04eb  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x05e0  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x05f0  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x05f3  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x062b  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x071b  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x07d5  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x008e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:425:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x019b  */
    /* JADX WARN: Type inference failed for: r12v55 */
    /* JADX WARN: Type inference failed for: r12v56, types: [int] */
    /* JADX WARN: Type inference failed for: r12v60 */
    /* JADX WARN: Type inference failed for: r13v51, types: [androidx.constraintlayout.core.widgets.ConstraintWidgetContainer] */
    /* JADX WARN: Type inference failed for: r17v8, types: [boolean] */
    /* JADX WARN: Type inference failed for: r18v0 */
    /* JADX WARN: Type inference failed for: r18v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v11 */
    /* JADX WARN: Type inference failed for: r19v14 */
    /* JADX WARN: Type inference failed for: r19v15 */
    /* JADX WARN: Type inference failed for: r19v16 */
    /* JADX WARN: Type inference failed for: r27v10 */
    /* JADX WARN: Type inference failed for: r27v11 */
    /* JADX WARN: Type inference failed for: r27v12 */
    /* JADX WARN: Type inference failed for: r27v5 */
    /* JADX WARN: Type inference failed for: r27v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v16, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v17, types: [boolean] */
    /* JADX WARN: Type inference failed for: r59v0, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        int i;
        ?? r19;
        int i2;
        int i3;
        int i4;
        boolean[] zArr;
        boolean z3;
        boolean z4;
        int i5;
        boolean z5;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        WeakReference weakReference;
        Metrics metrics;
        ConstraintAnchor constraintAnchor;
        boolean[] zArr2;
        boolean[] zArr3;
        ConstraintAnchor constraintAnchor2;
        int i6;
        boolean z6;
        boolean z7;
        int i7;
        int i8;
        float f;
        SolverVariable solverVariable;
        int i9;
        int i10;
        int i11;
        boolean z8;
        int i12;
        DimensionBehaviour dimensionBehaviour;
        boolean z9;
        ConstraintAnchor constraintAnchor3;
        boolean z10;
        SolverVariable solverVariable2;
        DimensionBehaviour dimensionBehaviour2;
        SolverVariable solverVariable3;
        DimensionBehaviour dimensionBehaviour3;
        boolean z11;
        SolverVariable solverVariable4;
        boolean z12;
        int i13;
        ConstraintAnchor constraintAnchor4;
        int i14;
        SolverVariable solverVariable5;
        ConstraintAnchor constraintAnchor5;
        SolverVariable solverVariable6;
        ConstraintAnchor constraintAnchor6;
        SolverVariable solverVariable7;
        SolverVariable solverVariable8;
        SolverVariable solverVariable9;
        int i15;
        char c;
        int i16;
        int i17;
        SolverVariable solverVariable10;
        SolverVariable solverVariable11;
        Metrics metrics2;
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int i18;
        boolean zIsInHorizontalChain;
        ?? r12;
        boolean zIsInVerticalChain;
        HorizontalWidgetRun horizontalWidgetRun2;
        boolean z13;
        int size;
        int i19;
        int i20;
        LinearSystem linearSystem2 = linearSystem;
        ConstraintAnchor constraintAnchor7 = this.mLeft;
        SolverVariable solverVariableCreateObjectVariable = linearSystem2.createObjectVariable(constraintAnchor7);
        ConstraintAnchor constraintAnchor8 = this.mRight;
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem2.createObjectVariable(constraintAnchor8);
        ConstraintAnchor constraintAnchor9 = this.mTop;
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem2.createObjectVariable(constraintAnchor9);
        ConstraintAnchor constraintAnchor10 = this.mBottom;
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem2.createObjectVariable(constraintAnchor10);
        ConstraintAnchor constraintAnchor11 = this.mBaseline;
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem2.createObjectVariable(constraintAnchor11);
        ConstraintWidget constraintWidget3 = this.mParent;
        if (constraintWidget3 != null) {
            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget3.mListDimensionBehaviors;
            i = 0;
            DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[0];
            DimensionBehaviour dimensionBehaviour5 = DimensionBehaviour.WRAP_CONTENT;
            i2 = dimensionBehaviour4 == dimensionBehaviour5 ? 1 : 0;
            i3 = dimensionBehaviourArr[1] == dimensionBehaviour5 ? 1 : 0;
            int i21 = this.mWrapBehaviorInParent;
            if (i21 != 1) {
                boolean z14 = true;
                if (i21 != 2) {
                    z2 = z14;
                    r19 = z14;
                    if (i21 == 3) {
                    }
                } else {
                    i2 = 0;
                    r19 = z14;
                }
            } else {
                r19 = 1;
                i3 = 0;
            }
            i4 = this.mVisibility;
            zArr = this.mIsInBarrier;
            int i22 = i3;
            if (i4 == 8 && !this.mAnimated) {
                size = this.mAnchors.size();
                i19 = i;
                while (true) {
                    if (i19 >= size) {
                        i20 = size;
                        HashSet hashSet = ((ConstraintAnchor) this.mAnchors.get(i19)).mDependents;
                        if (hashSet != null && hashSet.size() > 0) {
                            break;
                        }
                        i19++;
                        size = i20;
                    } else if (!zArr[i] && !zArr[r19]) {
                        return;
                    }
                }
            }
            z3 = this.mResolvedHorizontal;
            if (!z3 || this.mResolvedVertical) {
                boolean z15 = this.mOptimizeWrapOnResolved;
                if (!z3) {
                    linearSystem2.addEquality(solverVariableCreateObjectVariable, this.mX);
                    linearSystem2.addEquality(solverVariableCreateObjectVariable2, this.mX + this.mWidth);
                    if (i2 == 0 || (constraintWidget2 = this.mParent) == null) {
                        z4 = z15;
                        i5 = i2;
                    } else if (z15) {
                        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
                        z4 = z15;
                        WeakReference weakReference2 = constraintWidgetContainer.mHorizontalWrapMin;
                        if (weakReference2 == null || weakReference2.get() == null) {
                            i5 = i2;
                        } else {
                            i5 = i2;
                            if (constraintAnchor7.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer.mHorizontalWrapMin.get()).getFinalValue()) {
                            }
                            weakReference = constraintWidgetContainer.mHorizontalWrapMax;
                            if (weakReference != null || weakReference.get() == null || constraintAnchor8.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer.mHorizontalWrapMax.get()).getFinalValue()) {
                                constraintWidgetContainer.mHorizontalWrapMax = new WeakReference(constraintAnchor8);
                            }
                        }
                        constraintWidgetContainer.mHorizontalWrapMin = new WeakReference(constraintAnchor7);
                        weakReference = constraintWidgetContainer.mHorizontalWrapMax;
                        if (weakReference != null) {
                            constraintWidgetContainer.mHorizontalWrapMax = new WeakReference(constraintAnchor8);
                        }
                    } else {
                        z4 = z15;
                        i5 = i2;
                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget2.mRight), solverVariableCreateObjectVariable2, i, 5);
                    }
                    if (this.mResolvedVertical) {
                        linearSystem2.addEquality(solverVariableCreateObjectVariable3, this.mY);
                        linearSystem2.addEquality(solverVariableCreateObjectVariable4, this.mY + this.mHeight);
                        HashSet hashSet2 = constraintAnchor11.mDependents;
                        if (hashSet2 != null && hashSet2.size() > 0) {
                            linearSystem2.addEquality(solverVariableCreateObjectVariable5, this.mY + this.mBaselineDistance);
                        }
                        if (i22 == 0 || (constraintWidget = this.mParent) == null) {
                            z5 = false;
                            if (this.mResolvedHorizontal && this.mResolvedVertical) {
                                this.mResolvedHorizontal = z5;
                                this.mResolvedVertical = z5;
                                return;
                            }
                        } else if (z4) {
                            ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget;
                            WeakReference weakReference3 = constraintWidgetContainer2.mVerticalWrapMin;
                            if (weakReference3 == null || weakReference3.get() == null || constraintAnchor9.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer2.mVerticalWrapMin.get()).getFinalValue()) {
                                constraintWidgetContainer2.mVerticalWrapMin = new WeakReference(constraintAnchor9);
                            }
                            WeakReference weakReference4 = constraintWidgetContainer2.mVerticalWrapMax;
                            if (weakReference4 == null || weakReference4.get() == null || constraintAnchor10.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer2.mVerticalWrapMax.get()).getFinalValue()) {
                                constraintWidgetContainer2.mVerticalWrapMax = new WeakReference(constraintAnchor10);
                            }
                            z5 = false;
                            if (this.mResolvedHorizontal) {
                                this.mResolvedHorizontal = z5;
                                this.mResolvedVertical = z5;
                                return;
                            }
                        } else {
                            z5 = false;
                            linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget.mBottom), solverVariableCreateObjectVariable4, 0, 5);
                            if (this.mResolvedHorizontal) {
                            }
                        }
                    }
                }
            } else {
                i5 = i2;
            }
            metrics = LinearSystem.sMetrics;
            if (metrics != null) {
                metrics.widgets++;
            }
            boolean[] zArr4 = this.isTerminalWidget;
            if (z || (horizontalWidgetRun2 = this.mHorizontalRun) == null) {
                constraintAnchor = constraintAnchor11;
                zArr2 = zArr;
            } else {
                constraintAnchor = constraintAnchor11;
                VerticalWidgetRun verticalWidgetRun2 = this.mVerticalRun;
                zArr2 = zArr;
                if (verticalWidgetRun2 != null) {
                    DependencyNode dependencyNode = horizontalWidgetRun2.start;
                    zArr3 = zArr4;
                    if (dependencyNode.resolved && horizontalWidgetRun2.end.resolved && verticalWidgetRun2.start.resolved && verticalWidgetRun2.end.resolved) {
                        if (metrics != null) {
                            metrics.graphSolved++;
                        }
                        linearSystem2.addEquality(solverVariableCreateObjectVariable, dependencyNode.value);
                        linearSystem2.addEquality(solverVariableCreateObjectVariable2, this.mHorizontalRun.end.value);
                        linearSystem2.addEquality(solverVariableCreateObjectVariable3, this.mVerticalRun.start.value);
                        linearSystem2.addEquality(solverVariableCreateObjectVariable4, this.mVerticalRun.end.value);
                        linearSystem2.addEquality(solverVariableCreateObjectVariable5, this.mVerticalRun.baseline.value);
                        if (this.mParent == null) {
                            z13 = false;
                        } else {
                            if (i5 != 0 && zArr3[0] && !isInHorizontalChain()) {
                                linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable2, 0, 8);
                            }
                            if (i22 != 0 && zArr3[r19] && !isInVerticalChain()) {
                                z13 = false;
                                linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable4, 0, 8);
                            }
                        }
                        this.mResolvedHorizontal = z13;
                        this.mResolvedVertical = z13;
                        return;
                    }
                }
                if (metrics != null) {
                    metrics.linearSolved++;
                }
                if (this.mParent != null) {
                    if (isChainHead(0)) {
                        ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                        zIsInHorizontalChain = r19;
                        r12 = zIsInHorizontalChain;
                    } else {
                        zIsInHorizontalChain = isInHorizontalChain();
                        r12 = r19;
                    }
                    if (isChainHead(r12)) {
                        ((ConstraintWidgetContainer) this.mParent).addChain(this, r12);
                        zIsInVerticalChain = true;
                    } else {
                        zIsInVerticalChain = isInVerticalChain();
                    }
                    if (!zIsInHorizontalChain && i5 != 0 && this.mVisibility != 8 && constraintAnchor7.mTarget == null && constraintAnchor8.mTarget == null) {
                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable2, 0, 1);
                    }
                    if (!zIsInVerticalChain && i22 != 0 && this.mVisibility != 8 && constraintAnchor9.mTarget == null && constraintAnchor10.mTarget == null && constraintAnchor == null) {
                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable4, 0, 1);
                    }
                    z6 = zIsInHorizontalChain;
                    constraintAnchor2 = constraintAnchor8;
                    i6 = i22;
                    z7 = zIsInVerticalChain;
                } else {
                    constraintAnchor2 = constraintAnchor8;
                    i6 = i22;
                    z6 = false;
                    z7 = false;
                }
                i7 = this.mWidth;
                i8 = this.mMinWidth;
                if (i7 >= i8) {
                    i8 = i7;
                }
                int i23 = this.mHeight;
                int i24 = this.mMinHeight;
                int i25 = i23 < i24 ? i24 : i23;
                DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
                DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
                int i26 = i6;
                DimensionBehaviour dimensionBehaviour7 = DimensionBehaviour.MATCH_CONSTRAINT;
                boolean z16 = dimensionBehaviour6 != dimensionBehaviour7;
                DimensionBehaviour dimensionBehaviour8 = dimensionBehaviourArr2[1];
                boolean z17 = dimensionBehaviour8 != dimensionBehaviour7;
                int i27 = this.mDimensionRatioSide;
                this.mResolvedDimensionRatioSide = i27;
                f = this.mDimensionRatio;
                this.mResolvedDimensionRatio = f;
                int i28 = this.mMatchConstraintDefaultWidth;
                int i29 = this.mMatchConstraintDefaultHeight;
                if (f > 0.0f) {
                    solverVariable = solverVariableCreateObjectVariable4;
                    if (this.mVisibility != 8) {
                        int i30 = (dimensionBehaviour6 == dimensionBehaviour7 && i28 == 0) ? 3 : i28;
                        int i31 = (dimensionBehaviour8 == dimensionBehaviour7 && i29 == 0) ? 3 : i29;
                        if (dimensionBehaviour6 == dimensionBehaviour7 && dimensionBehaviour8 == dimensionBehaviour7) {
                            ConstraintAnchor constraintAnchor12 = constraintAnchor2;
                            if (i30 == 3 && i31 == 3) {
                                if (i27 == -1) {
                                    if (z16 && !z17) {
                                        this.mResolvedDimensionRatioSide = 0;
                                    } else if (!z16 && z17) {
                                        this.mResolvedDimensionRatioSide = 1;
                                        if (i27 == -1) {
                                            this.mResolvedDimensionRatio = 1.0f / f;
                                        }
                                    }
                                }
                                if (this.mResolvedDimensionRatioSide == 0 && (!constraintAnchor9.isConnected() || !constraintAnchor10.isConnected())) {
                                    this.mResolvedDimensionRatioSide = 1;
                                } else if (this.mResolvedDimensionRatioSide == 1 && (!constraintAnchor7.isConnected() || !constraintAnchor12.isConnected())) {
                                    this.mResolvedDimensionRatioSide = 0;
                                }
                                if (this.mResolvedDimensionRatioSide == -1 && (!constraintAnchor9.isConnected() || !constraintAnchor10.isConnected() || !constraintAnchor7.isConnected() || !constraintAnchor12.isConnected())) {
                                    if (constraintAnchor9.isConnected() && constraintAnchor10.isConnected()) {
                                        this.mResolvedDimensionRatioSide = 0;
                                    } else if (constraintAnchor7.isConnected() && constraintAnchor12.isConnected()) {
                                        this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                                        this.mResolvedDimensionRatioSide = 1;
                                    }
                                }
                                if (this.mResolvedDimensionRatioSide == -1) {
                                    int i32 = this.mMatchConstraintMinWidth;
                                    if (i32 > 0 && this.mMatchConstraintMinHeight == 0) {
                                        this.mResolvedDimensionRatioSide = 0;
                                    } else if (i32 == 0 && this.mMatchConstraintMinHeight > 0) {
                                        this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                                        this.mResolvedDimensionRatioSide = 1;
                                    }
                                }
                            }
                            i10 = i30;
                            i9 = i25;
                            z8 = true;
                            i11 = i31;
                        } else {
                            if (dimensionBehaviour6 == dimensionBehaviour7 && i30 == 3) {
                                this.mResolvedDimensionRatioSide = 0;
                                i8 = (int) (i23 * f);
                                if (dimensionBehaviour8 != dimensionBehaviour7) {
                                    i9 = i25;
                                    i10 = 4;
                                    z8 = false;
                                }
                                i11 = i31;
                            } else if (dimensionBehaviour8 == dimensionBehaviour7 && i31 == 3) {
                                this.mResolvedDimensionRatioSide = 1;
                                if (i27 == -1) {
                                    this.mResolvedDimensionRatio = 1.0f / f;
                                }
                                i9 = (int) (this.mResolvedDimensionRatio * i7);
                                i10 = i30;
                                if (dimensionBehaviour6 != dimensionBehaviour7) {
                                    i11 = 4;
                                } else {
                                    i11 = i31;
                                    z8 = true;
                                }
                            }
                            i10 = i30;
                            i9 = i25;
                            z8 = true;
                            i11 = i31;
                        }
                        int[] iArr = this.mResolvedMatchConstraintDefault;
                        iArr[0] = i10;
                        iArr[1] = i11;
                        if (z8) {
                            int i33 = this.mResolvedDimensionRatioSide;
                            i12 = -1;
                            boolean z18 = i33 == 0 || i33 == -1;
                            boolean z19 = !z8 && ((i18 = this.mResolvedDimensionRatioSide) == 1 || i18 == i12);
                            DimensionBehaviour dimensionBehaviour9 = this.mListDimensionBehaviors[0];
                            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                            z9 = dimensionBehaviour9 != dimensionBehaviour && (this instanceof ConstraintWidgetContainer);
                            if (z9) {
                                i8 = 0;
                            }
                            constraintAnchor3 = this.mCenter;
                            z10 = !constraintAnchor3.isConnected();
                            boolean z20 = zArr2[0];
                            boolean z21 = zArr2[1];
                            if (this.mHorizontalResolution != 2 || this.mResolvedHorizontal) {
                                solverVariable2 = solverVariableCreateObjectVariable;
                                dimensionBehaviour2 = dimensionBehaviour7;
                                solverVariable3 = solverVariableCreateObjectVariable2;
                                dimensionBehaviour3 = dimensionBehaviour;
                                z11 = z10;
                                solverVariable4 = solverVariableCreateObjectVariable5;
                                z12 = z6;
                                i13 = i5;
                                constraintAnchor4 = constraintAnchor;
                                i14 = i26;
                                solverVariable5 = solverVariableCreateObjectVariable3;
                                constraintAnchor5 = constraintAnchor10;
                                solverVariable6 = solverVariable;
                                constraintAnchor6 = constraintAnchor3;
                            } else {
                                if (z && (horizontalWidgetRun = this.mHorizontalRun) != null) {
                                    DependencyNode dependencyNode2 = horizontalWidgetRun.start;
                                    if (dependencyNode2.resolved && horizontalWidgetRun.end.resolved) {
                                        if (z) {
                                            linearSystem2.addEquality(solverVariableCreateObjectVariable, dependencyNode2.value);
                                            linearSystem2.addEquality(solverVariableCreateObjectVariable2, this.mHorizontalRun.end.value);
                                            if (this.mParent != null && i5 != 0 && zArr3[0] && !isInHorizontalChain()) {
                                                linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable2, 0, 8);
                                            }
                                        }
                                        solverVariable2 = solverVariableCreateObjectVariable;
                                        dimensionBehaviour2 = dimensionBehaviour7;
                                        solverVariable3 = solverVariableCreateObjectVariable2;
                                        dimensionBehaviour3 = dimensionBehaviour;
                                        z11 = z10;
                                        solverVariable4 = solverVariableCreateObjectVariable5;
                                        z12 = z6;
                                        i13 = i5;
                                        constraintAnchor4 = constraintAnchor;
                                        i14 = i26;
                                        solverVariable5 = solverVariableCreateObjectVariable3;
                                        constraintAnchor5 = constraintAnchor10;
                                        solverVariable6 = solverVariable;
                                        constraintAnchor6 = constraintAnchor3;
                                    }
                                }
                                ConstraintWidget constraintWidget4 = this.mParent;
                                SolverVariable solverVariableCreateObjectVariable6 = constraintWidget4 != null ? linearSystem2.createObjectVariable(constraintWidget4.mRight) : null;
                                ConstraintWidget constraintWidget5 = this.mParent;
                                SolverVariable solverVariableCreateObjectVariable7 = constraintWidget5 != null ? linearSystem2.createObjectVariable(constraintWidget5.mLeft) : null;
                                boolean z22 = zArr3[0];
                                DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
                                boolean z23 = z10;
                                DimensionBehaviour dimensionBehaviour10 = dimensionBehaviourArr3[0];
                                SolverVariable solverVariable12 = solverVariableCreateObjectVariable6;
                                int i34 = this.mX;
                                z12 = z6;
                                int i35 = i8;
                                int i36 = this.mMinWidth;
                                int i37 = this.mMaxDimension[0];
                                float f2 = this.mHorizontalBiasPercent;
                                boolean z24 = dimensionBehaviourArr3[1] == dimensionBehaviour7;
                                ?? r3 = i5;
                                dimensionBehaviour2 = dimensionBehaviour7;
                                solverVariable3 = solverVariableCreateObjectVariable2;
                                constraintAnchor4 = constraintAnchor;
                                ?? r4 = i26;
                                solverVariable5 = solverVariableCreateObjectVariable3;
                                constraintAnchor5 = constraintAnchor10;
                                dimensionBehaviour3 = dimensionBehaviour;
                                solverVariable6 = solverVariable;
                                solverVariable2 = solverVariableCreateObjectVariable;
                                solverVariable4 = solverVariableCreateObjectVariable5;
                                constraintAnchor6 = constraintAnchor3;
                                linearSystem2 = linearSystem;
                                applyConstraints(linearSystem2, true, r3, r4, z22, solverVariableCreateObjectVariable7, solverVariable12, dimensionBehaviour10, z9, this.mLeft, this.mRight, i34, i35, i36, i37, f2, z18, z24, z12, z7, z20, i10, i11, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z23);
                                i13 = r3;
                                i14 = r4;
                                z11 = z23;
                            }
                            if (z || (verticalWidgetRun = this.mVerticalRun) == null) {
                                solverVariable7 = solverVariable5;
                                solverVariable8 = solverVariable6;
                                solverVariable9 = solverVariable4;
                                i15 = 0;
                                c = 1;
                                i16 = 8;
                                i17 = 1;
                            } else {
                                DependencyNode dependencyNode3 = verticalWidgetRun.start;
                                if (dependencyNode3.resolved && verticalWidgetRun.end.resolved) {
                                    int i38 = dependencyNode3.value;
                                    solverVariable7 = solverVariable5;
                                    linearSystem2.addEquality(solverVariable7, i38);
                                    solverVariable8 = solverVariable6;
                                    linearSystem2.addEquality(solverVariable8, this.mVerticalRun.end.value);
                                    solverVariable9 = solverVariable4;
                                    linearSystem2.addEquality(solverVariable9, this.mVerticalRun.baseline.value);
                                    ConstraintWidget constraintWidget6 = this.mParent;
                                    if (constraintWidget6 == null || z7 || i14 == 0) {
                                        i15 = 0;
                                        c = 1;
                                    } else {
                                        c = 1;
                                        if (zArr3[1]) {
                                            i15 = 0;
                                            i16 = 8;
                                            linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget6.mBottom), solverVariable8, 0, 8);
                                            i17 = i15;
                                        } else {
                                            i15 = 0;
                                        }
                                    }
                                    i16 = 8;
                                    i17 = i15;
                                }
                            }
                            if (this.mVerticalResolution == 2) {
                                i17 = i15;
                            }
                            if (i17 != 0 || this.mResolvedVertical) {
                                solverVariable10 = solverVariable7;
                                solverVariable11 = solverVariable8;
                            } else {
                                ?? r9 = (this.mListDimensionBehaviors[c] == dimensionBehaviour3 && (this instanceof ConstraintWidgetContainer)) ? c : i15;
                                int i39 = r9 != 0 ? i15 : i9;
                                ConstraintWidget constraintWidget7 = this.mParent;
                                SolverVariable solverVariableCreateObjectVariable8 = constraintWidget7 != null ? linearSystem2.createObjectVariable(constraintWidget7.mBottom) : null;
                                ConstraintWidget constraintWidget8 = this.mParent;
                                SolverVariable solverVariableCreateObjectVariable9 = constraintWidget8 != null ? linearSystem2.createObjectVariable(constraintWidget8.mTop) : null;
                                int i40 = this.mBaselineDistance;
                                if (i40 <= 0) {
                                    ?? r27 = z11;
                                    if (this.mVisibility == i16) {
                                        ConstraintAnchor constraintAnchor13 = constraintAnchor4;
                                        if (constraintAnchor13.mTarget != null) {
                                            linearSystem2.addEquality(solverVariable9, solverVariable7, i40, i16);
                                            linearSystem2.addEquality(solverVariable9, linearSystem2.createObjectVariable(constraintAnchor13.mTarget), constraintAnchor13.getMargin(), i16);
                                            if (i14 != 0) {
                                                linearSystem2.addGreaterThan(solverVariableCreateObjectVariable8, linearSystem2.createObjectVariable(constraintAnchor5), i15, 5);
                                            }
                                            r27 = i15;
                                        } else if (this.mVisibility == i16) {
                                            linearSystem2.addEquality(solverVariable9, solverVariable7, constraintAnchor13.getMargin(), i16);
                                            r27 = z11;
                                        } else {
                                            linearSystem2.addEquality(solverVariable9, solverVariable7, i40, i16);
                                            r27 = z11;
                                        }
                                    }
                                    boolean z25 = zArr3[c];
                                    DimensionBehaviour[] dimensionBehaviourArr4 = this.mListDimensionBehaviors;
                                    int i41 = i15;
                                    char c2 = c;
                                    solverVariable11 = solverVariable8;
                                    solverVariable10 = solverVariable7;
                                    linearSystem2 = linearSystem;
                                    applyConstraints(linearSystem2, false, i14, i13, z25, solverVariableCreateObjectVariable9, solverVariableCreateObjectVariable8, dimensionBehaviourArr4[c], r9, this.mTop, this.mBottom, this.mY, i39, this.mMinHeight, this.mMaxDimension[c2], this.mVerticalBiasPercent, z19, dimensionBehaviourArr4[i41] == dimensionBehaviour2 ? c2 : i41, z7, z12, z21, i11, i10, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight, this.mMatchConstraintPercentHeight, r27);
                                }
                            }
                            if (z8) {
                                if (this.mResolvedDimensionRatioSide == 1) {
                                    float f3 = this.mResolvedDimensionRatio;
                                    ArrayRow arrayRowCreateRow = linearSystem2.createRow();
                                    arrayRowCreateRow.variables.put(solverVariable11, -1.0f);
                                    arrayRowCreateRow.variables.put(solverVariable10, 1.0f);
                                    arrayRowCreateRow.variables.put(solverVariable3, f3);
                                    arrayRowCreateRow.variables.put(solverVariable2, -f3);
                                    linearSystem2.addConstraint(arrayRowCreateRow);
                                } else {
                                    float f4 = this.mResolvedDimensionRatio;
                                    ArrayRow arrayRowCreateRow2 = linearSystem2.createRow();
                                    arrayRowCreateRow2.variables.put(solverVariable3, -1.0f);
                                    arrayRowCreateRow2.variables.put(solverVariable2, 1.0f);
                                    arrayRowCreateRow2.variables.put(solverVariable11, f4);
                                    arrayRowCreateRow2.variables.put(solverVariable10, -f4);
                                    linearSystem2.addConstraint(arrayRowCreateRow2);
                                }
                            }
                            if (constraintAnchor6.isConnected()) {
                                ConstraintAnchor constraintAnchor14 = constraintAnchor6;
                                ConstraintWidget constraintWidget9 = constraintAnchor14.mTarget.mOwner;
                                float radians = (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f);
                                int margin = constraintAnchor14.getMargin();
                                ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                                SolverVariable solverVariableCreateObjectVariable10 = linearSystem2.createObjectVariable(getAnchor(type));
                                ConstraintAnchor.Type type2 = ConstraintAnchor.Type.TOP;
                                SolverVariable solverVariableCreateObjectVariable11 = linearSystem2.createObjectVariable(getAnchor(type2));
                                ConstraintAnchor.Type type3 = ConstraintAnchor.Type.RIGHT;
                                SolverVariable solverVariableCreateObjectVariable12 = linearSystem2.createObjectVariable(getAnchor(type3));
                                ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
                                SolverVariable solverVariableCreateObjectVariable13 = linearSystem2.createObjectVariable(getAnchor(type4));
                                SolverVariable solverVariableCreateObjectVariable14 = linearSystem2.createObjectVariable(constraintWidget9.getAnchor(type));
                                SolverVariable solverVariableCreateObjectVariable15 = linearSystem2.createObjectVariable(constraintWidget9.getAnchor(type2));
                                SolverVariable solverVariableCreateObjectVariable16 = linearSystem2.createObjectVariable(constraintWidget9.getAnchor(type3));
                                SolverVariable solverVariableCreateObjectVariable17 = linearSystem2.createObjectVariable(constraintWidget9.getAnchor(type4));
                                ArrayRow arrayRowCreateRow3 = linearSystem2.createRow();
                                double d = radians;
                                double dSin = Math.sin(d);
                                double d2 = margin;
                                arrayRowCreateRow3.variables.put(solverVariableCreateObjectVariable15, 0.5f);
                                arrayRowCreateRow3.variables.put(solverVariableCreateObjectVariable17, 0.5f);
                                arrayRowCreateRow3.variables.put(solverVariableCreateObjectVariable11, -0.5f);
                                arrayRowCreateRow3.variables.put(solverVariableCreateObjectVariable13, -0.5f);
                                arrayRowCreateRow3.mConstantValue = -((float) (dSin * d2));
                                linearSystem2.addConstraint(arrayRowCreateRow3);
                                ArrayRow arrayRowCreateRow4 = linearSystem2.createRow();
                                float fCos = (float) (Math.cos(d) * d2);
                                arrayRowCreateRow4.variables.put(solverVariableCreateObjectVariable14, 0.5f);
                                arrayRowCreateRow4.variables.put(solverVariableCreateObjectVariable16, 0.5f);
                                arrayRowCreateRow4.variables.put(solverVariableCreateObjectVariable10, -0.5f);
                                arrayRowCreateRow4.variables.put(solverVariableCreateObjectVariable12, -0.5f);
                                arrayRowCreateRow4.mConstantValue = -fCos;
                                linearSystem2.addConstraint(arrayRowCreateRow4);
                            }
                            this.mResolvedHorizontal = false;
                            this.mResolvedVertical = false;
                            metrics2 = LinearSystem.sMetrics;
                            if (metrics2 == null) {
                                metrics2.mEquations = linearSystem2.mNumRows;
                                metrics2.mVariables = linearSystem2.mVariablesID;
                                return;
                            }
                            return;
                        }
                        i12 = -1;
                        if (z8) {
                        }
                        DimensionBehaviour dimensionBehaviour92 = this.mListDimensionBehaviors[0];
                        dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                        if (dimensionBehaviour92 != dimensionBehaviour) {
                        }
                        if (z9) {
                        }
                        constraintAnchor3 = this.mCenter;
                        z10 = !constraintAnchor3.isConnected();
                        boolean z202 = zArr2[0];
                        boolean z212 = zArr2[1];
                        if (this.mHorizontalResolution != 2) {
                            solverVariable2 = solverVariableCreateObjectVariable;
                            dimensionBehaviour2 = dimensionBehaviour7;
                            solverVariable3 = solverVariableCreateObjectVariable2;
                            dimensionBehaviour3 = dimensionBehaviour;
                            z11 = z10;
                            solverVariable4 = solverVariableCreateObjectVariable5;
                            z12 = z6;
                            i13 = i5;
                            constraintAnchor4 = constraintAnchor;
                            i14 = i26;
                            solverVariable5 = solverVariableCreateObjectVariable3;
                            constraintAnchor5 = constraintAnchor10;
                            solverVariable6 = solverVariable;
                            constraintAnchor6 = constraintAnchor3;
                        }
                        if (z) {
                            solverVariable7 = solverVariable5;
                            solverVariable8 = solverVariable6;
                            solverVariable9 = solverVariable4;
                            i15 = 0;
                            c = 1;
                            i16 = 8;
                            i17 = 1;
                        }
                        if (this.mVerticalResolution == 2) {
                        }
                        if (i17 != 0) {
                            solverVariable10 = solverVariable7;
                            solverVariable11 = solverVariable8;
                        }
                        if (z8) {
                        }
                        if (constraintAnchor6.isConnected()) {
                        }
                        this.mResolvedHorizontal = false;
                        this.mResolvedVertical = false;
                        metrics2 = LinearSystem.sMetrics;
                        if (metrics2 == null) {
                        }
                    }
                    z8 = false;
                    int[] iArr2 = this.mResolvedMatchConstraintDefault;
                    iArr2[0] = i10;
                    iArr2[1] = i11;
                    if (z8) {
                    }
                    if (z8) {
                    }
                    DimensionBehaviour dimensionBehaviour922 = this.mListDimensionBehaviors[0];
                    dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                    if (dimensionBehaviour922 != dimensionBehaviour) {
                    }
                    if (z9) {
                    }
                    constraintAnchor3 = this.mCenter;
                    z10 = !constraintAnchor3.isConnected();
                    boolean z2022 = zArr2[0];
                    boolean z2122 = zArr2[1];
                    if (this.mHorizontalResolution != 2) {
                    }
                    if (z) {
                    }
                    if (this.mVerticalResolution == 2) {
                    }
                    if (i17 != 0) {
                    }
                    if (z8) {
                    }
                    if (constraintAnchor6.isConnected()) {
                    }
                    this.mResolvedHorizontal = false;
                    this.mResolvedVertical = false;
                    metrics2 = LinearSystem.sMetrics;
                    if (metrics2 == null) {
                    }
                } else {
                    solverVariable = solverVariableCreateObjectVariable4;
                }
                i9 = i25;
                i10 = i28;
                i11 = i29;
                z8 = false;
                int[] iArr22 = this.mResolvedMatchConstraintDefault;
                iArr22[0] = i10;
                iArr22[1] = i11;
                if (z8) {
                }
                if (z8) {
                }
                DimensionBehaviour dimensionBehaviour9222 = this.mListDimensionBehaviors[0];
                dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour9222 != dimensionBehaviour) {
                }
                if (z9) {
                }
                constraintAnchor3 = this.mCenter;
                z10 = !constraintAnchor3.isConnected();
                boolean z20222 = zArr2[0];
                boolean z21222 = zArr2[1];
                if (this.mHorizontalResolution != 2) {
                }
                if (z) {
                }
                if (this.mVerticalResolution == 2) {
                }
                if (i17 != 0) {
                }
                if (z8) {
                }
                if (constraintAnchor6.isConnected()) {
                }
                this.mResolvedHorizontal = false;
                this.mResolvedVertical = false;
                metrics2 = LinearSystem.sMetrics;
                if (metrics2 == null) {
                }
            }
            zArr3 = zArr4;
            if (metrics != null) {
            }
            if (this.mParent != null) {
            }
            i7 = this.mWidth;
            i8 = this.mMinWidth;
            if (i7 >= i8) {
            }
            int i232 = this.mHeight;
            int i242 = this.mMinHeight;
            if (i232 < i242) {
            }
            DimensionBehaviour[] dimensionBehaviourArr22 = this.mListDimensionBehaviors;
            DimensionBehaviour dimensionBehaviour62 = dimensionBehaviourArr22[0];
            int i262 = i6;
            DimensionBehaviour dimensionBehaviour72 = DimensionBehaviour.MATCH_CONSTRAINT;
            if (dimensionBehaviour62 != dimensionBehaviour72) {
            }
            DimensionBehaviour dimensionBehaviour82 = dimensionBehaviourArr22[1];
            if (dimensionBehaviour82 != dimensionBehaviour72) {
            }
            int i272 = this.mDimensionRatioSide;
            this.mResolvedDimensionRatioSide = i272;
            f = this.mDimensionRatio;
            this.mResolvedDimensionRatio = f;
            int i282 = this.mMatchConstraintDefaultWidth;
            int i292 = this.mMatchConstraintDefaultHeight;
            if (f > 0.0f) {
            }
            i9 = i25;
            i10 = i282;
            i11 = i292;
            z8 = false;
            int[] iArr222 = this.mResolvedMatchConstraintDefault;
            iArr222[0] = i10;
            iArr222[1] = i11;
            if (z8) {
            }
            if (z8) {
            }
            DimensionBehaviour dimensionBehaviour92222 = this.mListDimensionBehaviors[0];
            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour92222 != dimensionBehaviour) {
            }
            if (z9) {
            }
            constraintAnchor3 = this.mCenter;
            z10 = !constraintAnchor3.isConnected();
            boolean z202222 = zArr2[0];
            boolean z212222 = zArr2[1];
            if (this.mHorizontalResolution != 2) {
            }
            if (z) {
            }
            if (this.mVerticalResolution == 2) {
            }
            if (i17 != 0) {
            }
            if (z8) {
            }
            if (constraintAnchor6.isConnected()) {
            }
            this.mResolvedHorizontal = false;
            this.mResolvedVertical = false;
            metrics2 = LinearSystem.sMetrics;
            if (metrics2 == null) {
            }
        } else {
            z2 = true;
            i = 0;
        }
        i3 = i;
        i2 = i3;
        r19 = z2;
        i4 = this.mVisibility;
        zArr = this.mIsInBarrier;
        int i222 = i3;
        if (i4 == 8) {
            size = this.mAnchors.size();
            i19 = i;
            while (true) {
                if (i19 >= size) {
                }
                i19++;
                size = i20;
            }
        }
        z3 = this.mResolvedHorizontal;
        if (z3) {
            boolean z152 = this.mOptimizeWrapOnResolved;
            if (!z3) {
            }
        }
        metrics = LinearSystem.sMetrics;
        if (metrics != null) {
        }
        boolean[] zArr42 = this.isTerminalWidget;
        if (z) {
            constraintAnchor = constraintAnchor11;
            zArr2 = zArr;
            zArr3 = zArr42;
        }
        if (metrics != null) {
        }
        if (this.mParent != null) {
        }
        i7 = this.mWidth;
        i8 = this.mMinWidth;
        if (i7 >= i8) {
        }
        int i2322 = this.mHeight;
        int i2422 = this.mMinHeight;
        if (i2322 < i2422) {
        }
        DimensionBehaviour[] dimensionBehaviourArr222 = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour622 = dimensionBehaviourArr222[0];
        int i2622 = i6;
        DimensionBehaviour dimensionBehaviour722 = DimensionBehaviour.MATCH_CONSTRAINT;
        if (dimensionBehaviour622 != dimensionBehaviour722) {
        }
        DimensionBehaviour dimensionBehaviour822 = dimensionBehaviourArr222[1];
        if (dimensionBehaviour822 != dimensionBehaviour722) {
        }
        int i2722 = this.mDimensionRatioSide;
        this.mResolvedDimensionRatioSide = i2722;
        f = this.mDimensionRatio;
        this.mResolvedDimensionRatio = f;
        int i2822 = this.mMatchConstraintDefaultWidth;
        int i2922 = this.mMatchConstraintDefaultHeight;
        if (f > 0.0f) {
        }
        i9 = i25;
        i10 = i2822;
        i11 = i2922;
        z8 = false;
        int[] iArr2222 = this.mResolvedMatchConstraintDefault;
        iArr2222[0] = i10;
        iArr2222[1] = i11;
        if (z8) {
        }
        if (z8) {
        }
        DimensionBehaviour dimensionBehaviour922222 = this.mListDimensionBehaviors[0];
        dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour922222 != dimensionBehaviour) {
        }
        if (z9) {
        }
        constraintAnchor3 = this.mCenter;
        z10 = !constraintAnchor3.isConnected();
        boolean z2022222 = zArr2[0];
        boolean z2122222 = zArr2[1];
        if (this.mHorizontalResolution != 2) {
        }
        if (z) {
        }
        if (this.mVerticalResolution == 2) {
        }
        if (i17 != 0) {
        }
        if (z8) {
        }
        if (constraintAnchor6.isConnected()) {
        }
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        metrics2 = LinearSystem.sMetrics;
        if (metrics2 == null) {
        }
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    /* JADX WARN: Removed duplicated region for block: B:219:0x03c0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x03cd  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x045c  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x049d A[PHI: r0
      0x049d: PHI (r0v16 int) = (r0v15 int), (r0v20 int), (r0v20 int), (r0v20 int) binds: [B:283:0x048d, B:285:0x0493, B:286:0x0495, B:288:0x0499] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:293:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x04de A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:348:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyConstraints(LinearSystem linearSystem, boolean z, boolean z2, boolean z3, boolean z4, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z5, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i, int i2, int i3, int i4, float f, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i5, int i6, int i7, int i8, float f2, boolean z11) {
        int iMin;
        SolverVariable solverVariable3;
        int i9;
        int i10;
        boolean z12;
        SolverVariable solverVariableCreateObjectVariable;
        SolverVariable solverVariableCreateObjectVariable2;
        ConstraintAnchor constraintAnchor3;
        SolverVariable solverVariable4;
        boolean z13;
        int i11;
        SolverVariable solverVariable5;
        SolverVariable solverVariable6;
        SolverVariable solverVariable7;
        SolverVariable solverVariable8;
        int i12;
        boolean z14;
        boolean z15;
        int i13;
        int i14;
        boolean z16;
        boolean z17;
        ConstraintWidget constraintWidget;
        int i15;
        int i16;
        ConstraintAnchor constraintAnchor4;
        boolean z18;
        int i17;
        boolean z19;
        int iMin2;
        int i18;
        int i19;
        HashSet hashSet;
        int i20;
        int i21;
        int i22;
        boolean z20;
        boolean z21;
        int i23;
        LinearSystem linearSystem2 = linearSystem;
        int i24 = i7;
        int i25 = i8;
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem2.createObjectVariable(constraintAnchor);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem2.createObjectVariable(constraintAnchor2);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem2.createObjectVariable(constraintAnchor.mTarget);
        SolverVariable solverVariableCreateObjectVariable6 = linearSystem2.createObjectVariable(constraintAnchor2.mTarget);
        boolean zIsConnected = constraintAnchor.isConnected();
        boolean zIsConnected2 = constraintAnchor2.isConnected();
        boolean zIsConnected3 = this.mCenter.isConnected();
        int i26 = zIsConnected2 ? (zIsConnected ? 1 : 0) + 1 : zIsConnected ? 1 : 0;
        if (zIsConnected3) {
            i26++;
        }
        int i27 = i26;
        int i28 = z6 ? 3 : i5;
        int iOrdinal = dimensionBehaviour.ordinal();
        boolean z22 = (iOrdinal == 0 || iOrdinal == 1 || iOrdinal != 2 || i28 == 4) ? false : true;
        int i29 = this.mWidthOverride;
        boolean z23 = z22;
        if (i29 != -1 && z) {
            this.mWidthOverride = -1;
            i2 = i29;
            z23 = false;
        }
        int i30 = this.mHeightOverride;
        if (i30 == -1 || z) {
            i30 = i2;
        } else {
            this.mHeightOverride = -1;
            z23 = false;
        }
        int i31 = i30;
        if (this.mVisibility == 8) {
            iMin = 0;
            z23 = false;
        } else {
            iMin = i31;
        }
        if (z11) {
            if (!zIsConnected && !zIsConnected2 && !zIsConnected3) {
                linearSystem2.addEquality(solverVariableCreateObjectVariable3, i);
            } else if (zIsConnected && !zIsConnected2) {
                solverVariable3 = solverVariableCreateObjectVariable6;
                i9 = 8;
                linearSystem2.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
            }
            solverVariable3 = solverVariableCreateObjectVariable6;
            i9 = 8;
        } else {
            solverVariable3 = solverVariableCreateObjectVariable6;
            i9 = 8;
        }
        if (z23) {
            if (i27 == 2 || z6 || !(i28 == 1 || i28 == 0)) {
                if (i24 == -2) {
                    i24 = iMin;
                }
                if (i25 == -2) {
                    i25 = iMin;
                }
                if (iMin > 0 && i28 != 1) {
                    iMin = 0;
                }
                if (i24 > 0) {
                    linearSystem2.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i24, 8);
                    iMin = Math.max(iMin, i24);
                }
                if (i25 > 0) {
                    if (!z2 || i28 != 1) {
                        linearSystem2.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i25, 8);
                    }
                    iMin = Math.min(iMin, i25);
                }
                if (i28 == 1) {
                    if (z2) {
                        linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                    } else if (z8) {
                        linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                        linearSystem2.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                    } else {
                        linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                        linearSystem2.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                    }
                } else if (i28 == 2) {
                    ConstraintAnchor.Type type = ConstraintAnchor.Type.TOP;
                    ConstraintAnchor.Type type2 = constraintAnchor.mType;
                    if (type2 == type || type2 == ConstraintAnchor.Type.BOTTOM) {
                        solverVariableCreateObjectVariable = linearSystem2.createObjectVariable(this.mParent.getAnchor(type));
                        solverVariableCreateObjectVariable2 = linearSystem2.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                    } else {
                        solverVariableCreateObjectVariable = linearSystem2.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                        solverVariableCreateObjectVariable2 = linearSystem2.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                    }
                    ArrayRow arrayRowCreateRow = linearSystem2.createRow();
                    int i32 = i24;
                    arrayRowCreateRow.variables.put(solverVariableCreateObjectVariable4, -1.0f);
                    arrayRowCreateRow.variables.put(solverVariableCreateObjectVariable3, 1.0f);
                    arrayRowCreateRow.variables.put(solverVariableCreateObjectVariable2, f2);
                    arrayRowCreateRow.variables.put(solverVariableCreateObjectVariable, -f2);
                    linearSystem2.addConstraint(arrayRowCreateRow);
                    if (z2) {
                        z23 = false;
                    }
                    z12 = z4;
                    i10 = i32;
                } else {
                    i10 = i24;
                    z12 = true;
                }
            } else {
                int iMax = Math.max(i24, iMin);
                if (i25 > 0) {
                    iMax = Math.min(i25, iMax);
                }
                linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMax, 8);
                z12 = z4;
                i10 = i24;
                z23 = false;
            }
            if (z11 || z8) {
                boolean z24 = z12;
                if (i27 >= 2 && z2 && z24) {
                    linearSystem2.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, 0, 8);
                    ConstraintAnchor constraintAnchor5 = this.mBaseline;
                    boolean z25 = z || constraintAnchor5.mTarget == null;
                    if (!z && (constraintAnchor3 = constraintAnchor5.mTarget) != null) {
                        ConstraintWidget constraintWidget2 = constraintAnchor3.mOwner;
                        if (constraintWidget2.mDimensionRatio != 0.0f) {
                            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget2.mListDimensionBehaviors;
                            DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
                            DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                            if (dimensionBehaviour2 == dimensionBehaviour3) {
                                z25 = true;
                                if (dimensionBehaviourArr[1] != dimensionBehaviour3) {
                                    z25 = false;
                                }
                            }
                        }
                    }
                    if (z25) {
                        linearSystem2.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, 0, 8);
                        return;
                    }
                    return;
                }
                return;
            }
            if (!zIsConnected && !zIsConnected2 && !zIsConnected3) {
                constraintAnchor4 = constraintAnchor2;
                solverVariable7 = solverVariableCreateObjectVariable4;
                z13 = z12;
                solverVariable4 = solverVariable3;
            } else if (!zIsConnected || zIsConnected2) {
                if (zIsConnected || !zIsConnected2) {
                    solverVariable4 = solverVariable3;
                    if (zIsConnected && zIsConnected2) {
                        ConstraintWidget constraintWidget3 = constraintAnchor.mTarget.mOwner;
                        ConstraintWidget constraintWidget4 = constraintAnchor2.mTarget.mOwner;
                        z13 = z12;
                        ConstraintWidget constraintWidget5 = this.mParent;
                        int i33 = 6;
                        if (z23) {
                            if (i28 == 0) {
                                if (i25 != 0 || i10 != 0) {
                                    z20 = true;
                                    z21 = false;
                                    z15 = true;
                                    i13 = 5;
                                    i14 = 5;
                                } else if (solverVariableCreateObjectVariable5.isFinalValue && solverVariable4.isFinalValue) {
                                    linearSystem2.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                                    linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariable4, -constraintAnchor2.getMargin(), 8);
                                    return;
                                } else {
                                    z20 = false;
                                    z21 = true;
                                    z15 = false;
                                    i13 = 8;
                                    i14 = 8;
                                }
                                if ((constraintWidget3 instanceof Barrier) || (constraintWidget4 instanceof Barrier)) {
                                    z14 = z20;
                                    solverVariable8 = solverVariableCreateObjectVariable5;
                                    linearSystem2 = linearSystem;
                                    solverVariable6 = solverVariableCreateObjectVariable3;
                                    solverVariable7 = solverVariableCreateObjectVariable4;
                                    i12 = 6;
                                    z16 = z21;
                                    i14 = 4;
                                } else {
                                    z14 = z20;
                                    solverVariable8 = solverVariableCreateObjectVariable5;
                                    linearSystem2 = linearSystem;
                                    solverVariable6 = solverVariableCreateObjectVariable3;
                                    solverVariable7 = solverVariableCreateObjectVariable4;
                                    i12 = 6;
                                    z16 = z21;
                                }
                                i11 = i28;
                            } else {
                                if (i28 == 2) {
                                    if ((constraintWidget3 instanceof Barrier) || (constraintWidget4 instanceof Barrier)) {
                                        linearSystem2 = linearSystem;
                                        i11 = i28;
                                        solverVariable6 = solverVariableCreateObjectVariable3;
                                        solverVariable7 = solverVariableCreateObjectVariable4;
                                        solverVariable8 = solverVariableCreateObjectVariable5;
                                        i12 = 6;
                                        z14 = true;
                                        z15 = true;
                                        i13 = 5;
                                    } else {
                                        linearSystem2 = linearSystem;
                                        i11 = i28;
                                        solverVariable6 = solverVariableCreateObjectVariable3;
                                        solverVariable7 = solverVariableCreateObjectVariable4;
                                        solverVariable8 = solverVariableCreateObjectVariable5;
                                        i12 = 6;
                                        z14 = true;
                                        z15 = true;
                                        i13 = 5;
                                        i14 = 5;
                                        z16 = false;
                                    }
                                } else if (i28 == 1) {
                                    linearSystem2 = linearSystem;
                                    i11 = i28;
                                    solverVariable6 = solverVariableCreateObjectVariable3;
                                    solverVariable7 = solverVariableCreateObjectVariable4;
                                    solverVariable8 = solverVariableCreateObjectVariable5;
                                    i12 = 6;
                                    z14 = true;
                                    z15 = true;
                                    i13 = 8;
                                } else if (i28 == 3) {
                                    i11 = i28;
                                    if (this.mResolvedDimensionRatioSide == -1) {
                                        if (z9) {
                                            linearSystem2 = linearSystem;
                                            solverVariable5 = solverVariable2;
                                            solverVariable6 = solverVariableCreateObjectVariable3;
                                            solverVariable7 = solverVariableCreateObjectVariable4;
                                            solverVariable8 = solverVariableCreateObjectVariable5;
                                            i12 = z2 ? 5 : 4;
                                        } else {
                                            linearSystem2 = linearSystem;
                                            solverVariable5 = solverVariable2;
                                            solverVariable6 = solverVariableCreateObjectVariable3;
                                            solverVariable7 = solverVariableCreateObjectVariable4;
                                            solverVariable8 = solverVariableCreateObjectVariable5;
                                            i12 = 8;
                                        }
                                        z14 = true;
                                        z15 = true;
                                        i13 = 8;
                                    } else if (z6) {
                                        if (i6 == 2 || i6 == 1) {
                                            i21 = 5;
                                            i22 = 4;
                                        } else {
                                            i21 = 8;
                                            i22 = 5;
                                        }
                                        i13 = i21;
                                        i14 = i22;
                                        solverVariable6 = solverVariableCreateObjectVariable3;
                                        solverVariable7 = solverVariableCreateObjectVariable4;
                                        solverVariable8 = solverVariableCreateObjectVariable5;
                                        i12 = 6;
                                        z14 = true;
                                        z15 = true;
                                        z16 = true;
                                        linearSystem2 = linearSystem;
                                    } else if (i25 > 0) {
                                        linearSystem2 = linearSystem;
                                        solverVariable5 = solverVariable2;
                                        solverVariable6 = solverVariableCreateObjectVariable3;
                                        solverVariable7 = solverVariableCreateObjectVariable4;
                                        solverVariable8 = solverVariableCreateObjectVariable5;
                                        i12 = 6;
                                        z14 = true;
                                        z15 = true;
                                        i13 = 5;
                                    } else {
                                        if (i25 != 0 || i10 != 0) {
                                            linearSystem2 = linearSystem;
                                            solverVariable5 = solverVariable2;
                                            solverVariable6 = solverVariableCreateObjectVariable3;
                                            solverVariable7 = solverVariableCreateObjectVariable4;
                                            solverVariable8 = solverVariableCreateObjectVariable5;
                                            i12 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i13 = 5;
                                            i14 = 4;
                                        } else {
                                            if (z9) {
                                                solverVariable5 = solverVariable2;
                                                i13 = (constraintWidget3 == constraintWidget5 || constraintWidget4 == constraintWidget5) ? 5 : 4;
                                                solverVariable6 = solverVariableCreateObjectVariable3;
                                                solverVariable7 = solverVariableCreateObjectVariable4;
                                                solverVariable8 = solverVariableCreateObjectVariable5;
                                                i12 = 6;
                                                z14 = true;
                                                z15 = true;
                                                i14 = 4;
                                                z16 = true;
                                                linearSystem2 = linearSystem;
                                                if (z15 && solverVariable8 == solverVariable4 && constraintWidget3 != constraintWidget5) {
                                                    z15 = false;
                                                    z17 = false;
                                                } else {
                                                    z17 = true;
                                                }
                                                if (z14) {
                                                    constraintWidget = constraintWidget4;
                                                    i15 = i10;
                                                    i16 = i11;
                                                    constraintAnchor4 = constraintAnchor2;
                                                    z18 = z17;
                                                    i17 = i13;
                                                    z19 = z2;
                                                } else {
                                                    if (z23 || z7 || z9 || solverVariable8 != solverVariable || solverVariable4 != solverVariable5) {
                                                        i20 = i12;
                                                        z18 = z17;
                                                        i17 = i13;
                                                        z19 = z2;
                                                    } else {
                                                        i20 = 8;
                                                        z19 = false;
                                                        i17 = 8;
                                                        z18 = false;
                                                    }
                                                    SolverVariable solverVariable9 = solverVariable8;
                                                    i15 = i10;
                                                    i16 = i11;
                                                    constraintWidget = constraintWidget4;
                                                    constraintAnchor4 = constraintAnchor2;
                                                    linearSystem2.addCentering(solverVariable6, solverVariable9, constraintAnchor.getMargin(), f, solverVariable4, solverVariable7, constraintAnchor2.getMargin(), i20);
                                                    solverVariable8 = solverVariable9;
                                                }
                                                if (this.mVisibility == 8 || ((hashSet = constraintAnchor4.mDependents) != null && hashSet.size() > 0)) {
                                                    if (z15) {
                                                        int i34 = (!z19 || solverVariable8 == solverVariable4 || z23 || !((constraintWidget3 instanceof Barrier) || (constraintWidget instanceof Barrier))) ? i17 : 6;
                                                        linearSystem2.addGreaterThan(solverVariable6, solverVariable8, constraintAnchor.getMargin(), i34);
                                                        linearSystem2.addLowerThan(solverVariable7, solverVariable4, -constraintAnchor4.getMargin(), i34);
                                                        i17 = i34;
                                                    }
                                                    if (z19 || !z10 || (constraintWidget3 instanceof Barrier) || (constraintWidget instanceof Barrier) || constraintWidget == constraintWidget5) {
                                                        iMin2 = i14;
                                                        i18 = i17;
                                                    } else {
                                                        iMin2 = 6;
                                                        i18 = 6;
                                                        z18 = true;
                                                    }
                                                    if (z18) {
                                                        if (z16 && (!z9 || z3)) {
                                                            if (constraintWidget3 != constraintWidget5 && constraintWidget != constraintWidget5) {
                                                                i33 = iMin2;
                                                            }
                                                            if ((constraintWidget3 instanceof Guideline) || (constraintWidget instanceof Guideline)) {
                                                                i33 = 5;
                                                            }
                                                            if ((constraintWidget3 instanceof Barrier) || (constraintWidget instanceof Barrier)) {
                                                                i33 = 5;
                                                            }
                                                            iMin2 = Math.max(z9 ? 5 : i33, iMin2);
                                                        }
                                                        if (z19) {
                                                            iMin2 = Math.min(i18, iMin2);
                                                            int i35 = (z6 && !z9 && (constraintWidget3 == constraintWidget5 || constraintWidget == constraintWidget5)) ? 4 : iMin2;
                                                            linearSystem2.addEquality(solverVariable6, solverVariable8, constraintAnchor.getMargin(), i35);
                                                            linearSystem2.addEquality(solverVariable7, solverVariable4, -constraintAnchor4.getMargin(), i35);
                                                        }
                                                        if (z19 && z13) {
                                                            int margin = constraintAnchor4.mTarget != null ? constraintAnchor4.getMargin() : 0;
                                                            if (solverVariable4 != solverVariable2) {
                                                                linearSystem2.addGreaterThan(solverVariable2, solverVariable7, margin, i23);
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                    if (z19) {
                                                        int margin2 = solverVariable == solverVariable8 ? constraintAnchor.getMargin() : 0;
                                                        if (solverVariable8 != solverVariable) {
                                                            linearSystem2.addGreaterThan(solverVariable6, solverVariable, margin2, 5);
                                                        }
                                                    }
                                                    if (z19 || !z23 || i3 != 0 || i15 != 0) {
                                                        i19 = 5;
                                                        i23 = i19;
                                                    } else if (z23 && i16 == 3) {
                                                        linearSystem2.addGreaterThan(solverVariable7, solverVariable6, 0, 8);
                                                        i19 = 5;
                                                        i23 = i19;
                                                    } else {
                                                        i19 = 5;
                                                        linearSystem2.addGreaterThan(solverVariable7, solverVariable6, 0, 5);
                                                        i23 = i19;
                                                    }
                                                    if (z19) {
                                                        return;
                                                    } else {
                                                        return;
                                                    }
                                                }
                                                return;
                                            }
                                            linearSystem2 = linearSystem;
                                            solverVariable5 = solverVariable2;
                                            solverVariable6 = solverVariableCreateObjectVariable3;
                                            solverVariable7 = solverVariableCreateObjectVariable4;
                                            solverVariable8 = solverVariableCreateObjectVariable5;
                                            i12 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i13 = 5;
                                            i14 = 8;
                                        }
                                        z16 = true;
                                        if (z15) {
                                            z17 = true;
                                            if (z14) {
                                            }
                                            if (this.mVisibility == 8) {
                                            }
                                            if (z15) {
                                            }
                                            if (z19) {
                                                iMin2 = i14;
                                                i18 = i17;
                                                if (z18) {
                                                }
                                                if (z19) {
                                                }
                                                if (z19) {
                                                    i19 = 5;
                                                    i23 = i19;
                                                }
                                            }
                                        }
                                        if (z19) {
                                        }
                                    }
                                    i14 = 5;
                                    z16 = true;
                                    if (z15) {
                                    }
                                    if (z19) {
                                    }
                                } else {
                                    i11 = i28;
                                    linearSystem2 = linearSystem;
                                    solverVariable5 = solverVariable2;
                                    solverVariable6 = solverVariableCreateObjectVariable3;
                                    solverVariable7 = solverVariableCreateObjectVariable4;
                                    solverVariable8 = solverVariableCreateObjectVariable5;
                                    i12 = 6;
                                    z14 = false;
                                    z15 = false;
                                }
                                i14 = 4;
                                z16 = false;
                            }
                            solverVariable5 = solverVariable2;
                            if (z15) {
                            }
                            if (z19) {
                            }
                        } else {
                            i11 = i28;
                            if (solverVariableCreateObjectVariable5.isFinalValue && solverVariable4.isFinalValue) {
                                linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariable4, solverVariableCreateObjectVariable4, constraintAnchor2.getMargin(), 8);
                                if (z2 && z13) {
                                    int margin3 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                                    if (solverVariable4 != solverVariable2) {
                                        linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, margin3, 5);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            linearSystem2 = linearSystem;
                            solverVariable5 = solverVariable2;
                            solverVariable6 = solverVariableCreateObjectVariable3;
                            solverVariable7 = solverVariableCreateObjectVariable4;
                            solverVariable8 = solverVariableCreateObjectVariable5;
                            i12 = 6;
                            z14 = true;
                            z15 = true;
                        }
                        i13 = 5;
                        i14 = 4;
                        z16 = false;
                        if (z15) {
                        }
                        if (z19) {
                        }
                    }
                } else {
                    solverVariable4 = solverVariable3;
                    linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariable4, -constraintAnchor2.getMargin(), 8);
                    if (z2) {
                        linearSystem2.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, 0, 5);
                        constraintAnchor4 = constraintAnchor2;
                        i19 = 5;
                        solverVariable7 = solverVariableCreateObjectVariable4;
                        z13 = z12;
                        z19 = z2;
                        i23 = i19;
                        if (z19) {
                        }
                    }
                }
                constraintAnchor4 = constraintAnchor2;
                solverVariable7 = solverVariableCreateObjectVariable4;
                z13 = z12;
            } else {
                z19 = z2;
                constraintAnchor4 = constraintAnchor2;
                solverVariable7 = solverVariableCreateObjectVariable4;
                z13 = z12;
                solverVariable4 = solverVariable3;
                i23 = (z2 && (constraintAnchor.mTarget.mOwner instanceof Barrier)) ? 8 : 5;
                if (z19) {
                }
            }
            i19 = 5;
            z19 = z2;
            i23 = i19;
            if (z19) {
            }
        } else if (z5) {
            linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 3);
            if (i3 > 0) {
                linearSystem2.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i3, i9);
            }
            if (i4 < Integer.MAX_VALUE) {
                linearSystem2.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i4, i9);
            }
        } else {
            linearSystem2.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i9);
        }
        z12 = z4;
        i10 = i24;
        if (z11) {
        }
        boolean z242 = z12;
        if (i27 >= 2) {
        }
    }

    public final void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        if (constraintAnchor.mOwner == this) {
            connect(constraintAnchor.mType, constraintAnchor2.mOwner, constraintAnchor2.mType, i);
        }
    }

    public void copy(ConstraintWidget constraintWidget, HashMap map) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = constraintWidget.mResolvedMatchConstraintDefault;
        int i = iArr[0];
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = i;
        iArr2[1] = iArr[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.mHasBaseline = constraintWidget.mHasBaseline;
        this.mInPlaceholder = constraintWidget.mInPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : (ConstraintWidget) map.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.mX = constraintWidget.mX;
        this.mY = constraintWidget.mY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mVisibility = constraintWidget.mVisibility;
        this.mAnimated = constraintWidget.mAnimated;
        this.mDebugName = constraintWidget.mDebugName;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        float[] fArr = constraintWidget.mWeight;
        float f = fArr[0];
        float[] fArr2 = this.mWeight;
        fArr2[0] = f;
        fArr2[1] = fArr[1];
        ConstraintWidget[] constraintWidgetArr = constraintWidget.mListNextMatchConstraintsWidget;
        ConstraintWidget constraintWidget2 = constraintWidgetArr[0];
        ConstraintWidget[] constraintWidgetArr2 = this.mListNextMatchConstraintsWidget;
        constraintWidgetArr2[0] = constraintWidget2;
        constraintWidgetArr2[1] = constraintWidgetArr[1];
        ConstraintWidget[] constraintWidgetArr3 = constraintWidget.mNextChainWidget;
        ConstraintWidget constraintWidget3 = constraintWidgetArr3[0];
        ConstraintWidget[] constraintWidgetArr4 = this.mNextChainWidget;
        constraintWidgetArr4[0] = constraintWidget3;
        constraintWidgetArr4[1] = constraintWidgetArr3[1];
        ConstraintWidget constraintWidget4 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget4 == null ? null : (ConstraintWidget) map.get(constraintWidget4);
        ConstraintWidget constraintWidget5 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget5 != null ? (ConstraintWidget) map.get(constraintWidget5) : null;
    }

    public final void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public final void ensureWidgetRuns() {
        if (this.mHorizontalRun == null) {
            this.mHorizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.mVerticalRun == null) {
            this.mVerticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final DimensionBehaviour getDimensionBehaviour(int i) {
        if (i == 0) {
            return this.mListDimensionBehaviors[0];
        }
        if (i == 1) {
            return this.mListDimensionBehaviors[1];
        }
        return null;
    }

    public final int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public final ConstraintWidget getNextChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public final ConstraintWidget getPreviousChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mTop).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public void getSceneString(StringBuilder sb) {
        sb.append("  " + this.stringId + ":{\n");
        StringBuilder sb2 = new StringBuilder("    actualWidth:");
        sb2.append(this.mWidth);
        sb.append(sb2.toString());
        sb.append("\n");
        sb.append("    actualHeight:" + this.mHeight);
        sb.append("\n");
        sb.append("    actualLeft:" + this.mX);
        sb.append("\n");
        sb.append("    actualTop:" + this.mY);
        sb.append("\n");
        getSceneString(sb, "left", this.mLeft);
        getSceneString(sb, "top", this.mTop);
        getSceneString(sb, "right", this.mRight);
        getSceneString(sb, "bottom", this.mBottom);
        getSceneString(sb, "baseline", this.mBaseline);
        getSceneString(sb, "centerX", this.mCenterX);
        getSceneString(sb, "centerY", this.mCenterY);
        int i = this.mWidth;
        int i2 = this.mMinWidth;
        int i3 = this.mMaxDimension[0];
        int i4 = this.mMatchConstraintMinWidth;
        int i5 = this.mMatchConstraintDefaultWidth;
        float f = this.mMatchConstraintPercentWidth;
        DimensionBehaviour dimensionBehaviour = this.mListDimensionBehaviors[0];
        float[] fArr = this.mWeight;
        float f2 = fArr[0];
        getSceneString(sb, "    width", i, i2, i3, i4, i5, f, dimensionBehaviour);
        int i6 = this.mHeight;
        int i7 = this.mMinHeight;
        int i8 = this.mMaxDimension[1];
        int i9 = this.mMatchConstraintMinHeight;
        int i10 = this.mMatchConstraintDefaultHeight;
        float f3 = this.mMatchConstraintPercentHeight;
        DimensionBehaviour dimensionBehaviour2 = this.mListDimensionBehaviors[1];
        float f4 = fArr[1];
        getSceneString(sb, "    height", i6, i7, i8, i9, i10, f3, dimensionBehaviour2);
        float f5 = this.mDimensionRatio;
        int i11 = this.mDimensionRatioSide;
        if (f5 != 0.0f) {
            sb.append("    dimensionRatio");
            sb.append(" :  [");
            sb.append(f5);
            sb.append(",");
            sb.append(i11);
            sb.append("");
            sb.append("],\n");
        }
        serializeAttribute(sb, "    horizontalBias", this.mHorizontalBiasPercent, 0.5f);
        serializeAttribute(sb, "    verticalBias", this.mVerticalBiasPercent, 0.5f);
        serializeAttribute(this.mHorizontalChainStyle, 0, "    horizontalChainStyle", sb);
        serializeAttribute(this.mVerticalChainStyle, 0, "    verticalChainStyle", sb);
        sb.append("  }");
    }

    public final int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public final int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.mX;
    }

    public final int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.mY;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x003a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x003b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean hasDanglingDimension(int i) {
        if (i == 0) {
            return (this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2;
        }
        if ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0) + (this.mBaseline.mTarget != null ? 1 : 0) < 2) {
        }
    }

    public final boolean hasResolvedTargets(int i, int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ConstraintAnchor constraintAnchor4;
        if (i == 0) {
            ConstraintAnchor constraintAnchor5 = this.mLeft;
            ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
            if (constraintAnchor6 == null || !constraintAnchor6.mHasFinalValue || (constraintAnchor4 = (constraintAnchor3 = this.mRight).mTarget) == null || !constraintAnchor4.mHasFinalValue) {
                return false;
            }
            return (constraintAnchor4.getFinalValue() - constraintAnchor3.getMargin()) - (constraintAnchor5.getMargin() + constraintAnchor5.mTarget.getFinalValue()) >= i2;
        }
        ConstraintAnchor constraintAnchor7 = this.mTop;
        ConstraintAnchor constraintAnchor8 = constraintAnchor7.mTarget;
        if (constraintAnchor8 == null || !constraintAnchor8.mHasFinalValue || (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) == null || !constraintAnchor2.mHasFinalValue) {
            return false;
        }
        return (constraintAnchor2.getFinalValue() - constraintAnchor.getMargin()) - (constraintAnchor7.getMargin() + constraintAnchor7.mTarget.getFinalValue()) >= i2;
    }

    public final void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public final boolean isChainHead(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i2];
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return (constraintAnchor4 == null || constraintAnchor4.mTarget == constraintAnchor3 || (constraintAnchor2 = (constraintAnchor = constraintAnchorArr[i2 + 1]).mTarget) == null || constraintAnchor2.mTarget != constraintAnchor) ? false : true;
    }

    public final boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public final boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public final boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public boolean isResolvedHorizontally() {
        if (this.mResolvedHorizontal) {
            return true;
        }
        return this.mLeft.mHasFinalValue && this.mRight.mHasFinalValue;
    }

    public boolean isResolvedVertically() {
        if (this.mResolvedVertical) {
            return true;
        }
        return this.mTop.mHasFinalValue && this.mBottom.mHasFinalValue;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = Float.NaN;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mVisibility = 0;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    public final void resetAnchors() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget != null && (constraintWidget instanceof ConstraintWidgetContainer)) {
            ((ConstraintWidgetContainer) constraintWidget).getClass();
        }
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            ((ConstraintAnchor) this.mAnchors.get(i)).reset();
        }
    }

    public final void resetFinalResolution() {
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) this.mAnchors.get(i);
            constraintAnchor.mHasFinalValue = false;
            constraintAnchor.mFinalValue = 0;
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable();
        this.mTop.resetSolverVariable();
        this.mRight.resetSolverVariable();
        this.mBottom.resetSolverVariable();
        this.mBaseline.resetSolverVariable();
        this.mCenter.resetSolverVariable();
        this.mCenterX.resetSolverVariable();
        this.mCenterY.resetSolverVariable();
    }

    public final void setBaselineDistance(int i) {
        this.mBaselineDistance = i;
        this.mHasBaseline = i > 0;
    }

    public final void setFinalHorizontal(int i, int i2) {
        if (this.mResolvedHorizontal) {
            return;
        }
        this.mLeft.setFinalValue(i);
        this.mRight.setFinalValue(i2);
        this.mX = i;
        this.mWidth = i2 - i;
        this.mResolvedHorizontal = true;
    }

    public final void setFinalVertical(int i, int i2) {
        if (this.mResolvedVertical) {
            return;
        }
        this.mTop.setFinalValue(i);
        this.mBottom.setFinalValue(i2);
        this.mY = i;
        this.mHeight = i2 - i;
        if (this.mHasBaseline) {
            this.mBaseline.setFinalValue(i + this.mBaselineDistance);
        }
        this.mResolvedVertical = true;
    }

    public final void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public final void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public final void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public final void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public String toString() {
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m("");
        sbM.append(this.mDebugName != null ? TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("id: "), this.mDebugName, " ") : "");
        sbM.append("(");
        sbM.append(this.mX);
        sbM.append(", ");
        sbM.append(this.mY);
        sbM.append(") - (");
        sbM.append(this.mWidth);
        sbM.append(" x ");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mHeight, ")", sbM);
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        HorizontalWidgetRun horizontalWidgetRun = this.mHorizontalRun;
        boolean z3 = z & horizontalWidgetRun.mResolved;
        VerticalWidgetRun verticalWidgetRun = this.mVerticalRun;
        boolean z4 = z2 & verticalWidgetRun.mResolved;
        int i3 = horizontalWidgetRun.start.value;
        int i4 = verticalWidgetRun.start.value;
        int i5 = horizontalWidgetRun.end.value;
        int i6 = verticalWidgetRun.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i6 = 0;
            i3 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (z3) {
            this.mX = i3;
        }
        if (z4) {
            this.mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (z3) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (z4) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i9 < (i = this.mHeight)) {
                i9 = i;
            }
            this.mHeight = i9;
            int i11 = this.mMinHeight;
            if (i9 < i11) {
                this.mHeight = i11;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem, boolean z) {
        int i;
        int i2;
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        ConstraintAnchor constraintAnchor = this.mLeft;
        linearSystem.getClass();
        int objectVariableValue = LinearSystem.getObjectVariableValue(constraintAnchor);
        int objectVariableValue2 = LinearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = LinearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = LinearSystem.getObjectVariableValue(this.mBottom);
        if (z && (horizontalWidgetRun = this.mHorizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    objectVariableValue = dependencyNode.value;
                    objectVariableValue3 = dependencyNode2.value;
                }
            }
        }
        if (z && (verticalWidgetRun = this.mVerticalRun) != null) {
            DependencyNode dependencyNode3 = verticalWidgetRun.start;
            if (dependencyNode3.resolved) {
                DependencyNode dependencyNode4 = verticalWidgetRun.end;
                if (dependencyNode4.resolved) {
                    objectVariableValue2 = dependencyNode3.value;
                    objectVariableValue4 = dependencyNode4.value;
                }
            }
        }
        int i3 = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i3 < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
            objectVariableValue4 = 0;
        }
        int i4 = objectVariableValue3 - objectVariableValue;
        int i5 = objectVariableValue4 - objectVariableValue2;
        this.mX = objectVariableValue;
        this.mY = objectVariableValue2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i4 < (i2 = this.mWidth)) {
            i4 = i2;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i5 < (i = this.mHeight)) {
            i5 = i;
        }
        this.mWidth = i4;
        this.mHeight = i5;
        int i6 = this.mMinHeight;
        if (i5 < i6) {
            this.mHeight = i6;
        }
        int i7 = this.mMinWidth;
        if (i4 < i7) {
            this.mWidth = i7;
        }
        int i8 = this.mMatchConstraintMaxWidth;
        if (i8 > 0 && dimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, i8);
        }
        int i9 = this.mMatchConstraintMaxHeight;
        if (i9 > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, i9);
        }
        int i10 = this.mWidth;
        if (i4 != i10) {
            this.mWidthOverride = i10;
        }
        int i11 = this.mHeight;
        if (i5 != i11) {
            this.mHeightOverride = i11;
        }
    }

    public final void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i) {
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        boolean z;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER;
        if (type == type5) {
            if (type2 == type5) {
                ConstraintAnchor.Type type6 = ConstraintAnchor.Type.LEFT;
                ConstraintAnchor anchor = getAnchor(type6);
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.RIGHT;
                ConstraintAnchor anchor2 = getAnchor(type7);
                ConstraintAnchor.Type type8 = ConstraintAnchor.Type.TOP;
                ConstraintAnchor anchor3 = getAnchor(type8);
                ConstraintAnchor.Type type9 = ConstraintAnchor.Type.BOTTOM;
                ConstraintAnchor anchor4 = getAnchor(type9);
                boolean z2 = true;
                if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                    connect(type6, constraintWidget, type6, 0);
                    connect(type7, constraintWidget, type7, 0);
                    z = true;
                } else {
                    z = false;
                }
                if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                    connect(type8, constraintWidget, type8, 0);
                    connect(type9, constraintWidget, type9, 0);
                } else {
                    z2 = false;
                }
                if (z && z2) {
                    getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                    return;
                }
                if (z) {
                    ConstraintAnchor.Type type10 = ConstraintAnchor.Type.CENTER_X;
                    getAnchor(type10).connect(constraintWidget.getAnchor(type10), 0);
                    return;
                } else {
                    if (z2) {
                        ConstraintAnchor.Type type11 = ConstraintAnchor.Type.CENTER_Y;
                        getAnchor(type11).connect(constraintWidget.getAnchor(type11), 0);
                        return;
                    }
                    return;
                }
            }
            ConstraintAnchor.Type type12 = ConstraintAnchor.Type.LEFT;
            if (type2 != type12 && type2 != ConstraintAnchor.Type.RIGHT) {
                ConstraintAnchor.Type type13 = ConstraintAnchor.Type.TOP;
                if (type2 == type13 || type2 == ConstraintAnchor.Type.BOTTOM) {
                    connect(type13, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type2, 0);
                    getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                }
                return;
            }
            connect(type12, constraintWidget, type2, 0);
            connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type2, 0);
            getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor.Type type14 = ConstraintAnchor.Type.CENTER_X;
        if (type == type14 && (type2 == (type4 = ConstraintAnchor.Type.LEFT) || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor anchor5 = getAnchor(type4);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.RIGHT);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type14).connect(anchor6, 0);
            return;
        }
        ConstraintAnchor.Type type15 = ConstraintAnchor.Type.CENTER_Y;
        if (type == type15 && (type2 == (type3 = ConstraintAnchor.Type.TOP) || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type3).connect(anchor8, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(anchor8, 0);
            getAnchor(type15).connect(anchor8, 0);
            return;
        }
        if (type == type14 && type2 == type14) {
            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.LEFT;
            getAnchor(type16).connect(constraintWidget.getAnchor(type16), 0);
            ConstraintAnchor.Type type17 = ConstraintAnchor.Type.RIGHT;
            getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
            getAnchor(type14).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        if (type == type15 && type2 == type15) {
            ConstraintAnchor.Type type18 = ConstraintAnchor.Type.TOP;
            getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
            ConstraintAnchor.Type type19 = ConstraintAnchor.Type.BOTTOM;
            getAnchor(type19).connect(constraintWidget.getAnchor(type19), 0);
            getAnchor(type15).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor anchor9 = getAnchor(type);
        ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
        if (anchor9.isValidConnection(anchor10)) {
            ConstraintAnchor.Type type20 = ConstraintAnchor.Type.BASELINE;
            if (type == type20) {
                ConstraintAnchor anchor11 = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor anchor12 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                if (anchor11 != null) {
                    anchor11.reset();
                }
                if (anchor12 != null) {
                    anchor12.reset();
                }
            } else if (type != ConstraintAnchor.Type.TOP && type != ConstraintAnchor.Type.BOTTOM) {
                if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                    ConstraintAnchor anchor13 = getAnchor(type5);
                    if (anchor13.mTarget != anchor10) {
                        anchor13.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(type).getOpposite();
                    ConstraintAnchor anchor14 = getAnchor(type14);
                    if (anchor14.isConnected()) {
                        opposite.reset();
                        anchor14.reset();
                    }
                }
            } else {
                ConstraintAnchor anchor15 = getAnchor(type20);
                if (anchor15 != null) {
                    anchor15.reset();
                }
                ConstraintAnchor anchor16 = getAnchor(type5);
                if (anchor16.mTarget != anchor10) {
                    anchor16.reset();
                }
                ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                ConstraintAnchor anchor17 = getAnchor(type15);
                if (anchor17.isConnected()) {
                    opposite2.reset();
                    anchor17.reset();
                }
            }
            anchor9.connect(anchor10, i);
        }
    }

    public static void serializeAttribute(int i, int i2, String str, StringBuilder sb) {
        if (i == i2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(i);
        sb.append(",\n");
    }

    public static void getSceneString(StringBuilder sb, String str, int i, int i2, int i3, int i4, int i5, float f, DimensionBehaviour dimensionBehaviour) {
        sb.append(str);
        sb.append(" :  {\n");
        String string = dimensionBehaviour.toString();
        if (!DimensionBehaviour.FIXED.toString().equals(string)) {
            MoveResult$$ExternalSyntheticOutline0.m(sb, "      behavior", " :   ", string, ",\n");
        }
        serializeAttribute(i, 0, "      size", sb);
        serializeAttribute(i2, 0, "      min", sb);
        serializeAttribute(i3, Integer.MAX_VALUE, "      max", sb);
        serializeAttribute(i4, 0, "      matchMin", sb);
        serializeAttribute(i5, 0, "      matchDef", sb);
        serializeAttribute(sb, "      matchPercent", f, 1.0f);
        sb.append("    },\n");
    }

    public static void getSceneString(StringBuilder sb, String str, ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append("    ");
        sb.append(str);
        sb.append(" : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("'");
        if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE || constraintAnchor.mMargin != 0) {
            sb.append(",");
            sb.append(constraintAnchor.mMargin);
            if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE) {
                sb.append(",");
                sb.append(constraintAnchor.mGoneMargin);
                sb.append(",");
            }
        }
        sb.append(" ] ,\n");
    }

    public ConstraintWidget(String str) {
        this.measured = false;
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mLeft = constraintAnchor;
        ConstraintAnchor constraintAnchor2 = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mTop = constraintAnchor2;
        ConstraintAnchor constraintAnchor3 = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mRight = constraintAnchor3;
        ConstraintAnchor constraintAnchor4 = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBottom = constraintAnchor4;
        ConstraintAnchor constraintAnchor5 = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mBaseline = constraintAnchor5;
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor6 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor6;
        this.mListAnchors = new ConstraintAnchor[]{constraintAnchor, constraintAnchor3, constraintAnchor2, constraintAnchor4, constraintAnchor5, constraintAnchor6};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
        this.mDebugName = str;
    }

    public ConstraintWidget(int i, int i2, int i3, int i4) {
        this.measured = false;
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mLeft = constraintAnchor;
        ConstraintAnchor constraintAnchor2 = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mTop = constraintAnchor2;
        ConstraintAnchor constraintAnchor3 = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mRight = constraintAnchor3;
        ConstraintAnchor constraintAnchor4 = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBottom = constraintAnchor4;
        ConstraintAnchor constraintAnchor5 = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mBaseline = constraintAnchor5;
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor6 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor6;
        this.mListAnchors = new ConstraintAnchor[]{constraintAnchor, constraintAnchor3, constraintAnchor2, constraintAnchor4, constraintAnchor5, constraintAnchor6};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        addAnchors();
    }

    public ConstraintWidget(String str, int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4);
        this.mDebugName = str;
    }

    public ConstraintWidget(int i, int i2) {
        this(0, 0, i, i2);
    }

    public ConstraintWidget(String str, int i, int i2) {
        this(i, i2);
        this.mDebugName = str;
    }
}
