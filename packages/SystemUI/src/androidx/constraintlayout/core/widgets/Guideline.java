package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Guideline extends ConstraintWidget {
    public boolean mResolved;
    public float mRelativePercent = -1.0f;
    public int mRelativeBegin = -1;
    public int mRelativeEnd = -1;
    public ConstraintAnchor mAnchor = this.mTop;
    public int mOrientation = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.constraintlayout.core.widgets.Guideline$1, reason: invalid class name */
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
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 3;
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

    public Guideline() {
        this.mAnchors.clear();
        this.mAnchors.add(this.mAnchor);
        int length = this.mListAnchors.length;
        for (int i = 0; i < length; i++) {
            this.mListAnchors[i] = this.mAnchor;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void addToSolver(LinearSystem linearSystem, boolean z) {
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) this.mParent;
        if (constraintWidgetContainer == null) {
            return;
        }
        Object anchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
        Object anchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintWidget constraintWidget = this.mParent;
        boolean z2 = constraintWidget != null && constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (this.mOrientation == 0) {
            anchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
            anchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintWidget constraintWidget2 = this.mParent;
            z2 = constraintWidget2 != null && constraintWidget2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        if (this.mResolved) {
            ConstraintAnchor constraintAnchor = this.mAnchor;
            if (constraintAnchor.mHasFinalValue) {
                SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintAnchor);
                linearSystem.addEquality(createObjectVariable, this.mAnchor.getFinalValue());
                if (this.mRelativeBegin != -1) {
                    if (z2) {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(anchor2), createObjectVariable, 0, 5);
                    }
                } else if (this.mRelativeEnd != -1 && z2) {
                    SolverVariable createObjectVariable2 = linearSystem.createObjectVariable(anchor2);
                    linearSystem.addGreaterThan(createObjectVariable, linearSystem.createObjectVariable(anchor), 0, 5);
                    linearSystem.addGreaterThan(createObjectVariable2, createObjectVariable, 0, 5);
                }
                this.mResolved = false;
                return;
            }
        }
        if (this.mRelativeBegin != -1) {
            SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(this.mAnchor);
            linearSystem.addEquality(createObjectVariable3, linearSystem.createObjectVariable(anchor), this.mRelativeBegin, 8);
            if (z2) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(anchor2), createObjectVariable3, 0, 5);
                return;
            }
            return;
        }
        if (this.mRelativeEnd != -1) {
            SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(this.mAnchor);
            SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(anchor2);
            linearSystem.addEquality(createObjectVariable4, createObjectVariable5, -this.mRelativeEnd, 8);
            if (z2) {
                linearSystem.addGreaterThan(createObjectVariable4, linearSystem.createObjectVariable(anchor), 0, 5);
                linearSystem.addGreaterThan(createObjectVariable5, createObjectVariable4, 0, 5);
                return;
            }
            return;
        }
        if (this.mRelativePercent != -1.0f) {
            SolverVariable createObjectVariable6 = linearSystem.createObjectVariable(this.mAnchor);
            SolverVariable createObjectVariable7 = linearSystem.createObjectVariable(anchor2);
            float f = this.mRelativePercent;
            ArrayRow createRow = linearSystem.createRow();
            createRow.variables.put(createObjectVariable6, -1.0f);
            createRow.variables.put(createObjectVariable7, f);
            linearSystem.addConstraint(createRow);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final boolean allowedInBarrier() {
        return true;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void copy(ConstraintWidget constraintWidget, HashMap hashMap) {
        super.copy(constraintWidget, hashMap);
        Guideline guideline = (Guideline) constraintWidget;
        this.mRelativePercent = guideline.mRelativePercent;
        this.mRelativeBegin = guideline.mRelativeBegin;
        this.mRelativeEnd = guideline.mRelativeEnd;
        setOrientation(guideline.mOrientation);
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        int i = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()];
        if (i == 1 || i == 2) {
            if (this.mOrientation == 1) {
                return this.mAnchor;
            }
            return null;
        }
        if ((i == 3 || i == 4) && this.mOrientation == 0) {
            return this.mAnchor;
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final boolean isResolvedHorizontally() {
        return this.mResolved;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final boolean isResolvedVertically() {
        return this.mResolved;
    }

    public final void setFinalValue(int i) {
        this.mAnchor.setFinalValue(i);
        this.mResolved = true;
    }

    public final void setOrientation(int i) {
        if (this.mOrientation == i) {
            return;
        }
        this.mOrientation = i;
        this.mAnchors.clear();
        if (this.mOrientation == 1) {
            this.mAnchor = this.mLeft;
        } else {
            this.mAnchor = this.mTop;
        }
        this.mAnchors.add(this.mAnchor);
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        int length = constraintAnchorArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            constraintAnchorArr[i2] = this.mAnchor;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void updateFromSolver(LinearSystem linearSystem, boolean z) {
        if (this.mParent == null) {
            return;
        }
        ConstraintAnchor constraintAnchor = this.mAnchor;
        linearSystem.getClass();
        int objectVariableValue = LinearSystem.getObjectVariableValue(constraintAnchor);
        if (this.mOrientation == 1) {
            this.mX = objectVariableValue;
            this.mY = 0;
            setHeight(this.mParent.getHeight());
            setWidth(0);
            return;
        }
        this.mX = 0;
        this.mY = objectVariableValue;
        setWidth(this.mParent.getWidth());
        setHeight(0);
    }
}
