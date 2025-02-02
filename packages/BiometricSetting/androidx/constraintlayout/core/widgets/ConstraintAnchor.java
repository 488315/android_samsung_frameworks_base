package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ConstraintAnchor {
    private int mFinalValue;
    private boolean mHasFinalValue;
    public final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    public ConstraintAnchor mTarget;
    public final Type mType;
    private HashSet<ConstraintAnchor> mDependents = null;
    public int mMargin = 0;
    int mGoneMargin = Integer.MIN_VALUE;

    public enum Type {
        /* JADX INFO: Fake field, exist only in values array */
        EF0,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y;

        Type() {
        }
    }

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        this.mOwner = constraintWidget;
        this.mType = type;
    }

    public final boolean connect(ConstraintAnchor constraintAnchor, int i, int i2, boolean z) {
        if (constraintAnchor == null) {
            reset();
            return true;
        }
        if (!z && !isValidConnection(constraintAnchor)) {
            return false;
        }
        this.mTarget = constraintAnchor;
        if (constraintAnchor.mDependents == null) {
            constraintAnchor.mDependents = new HashSet<>();
        }
        HashSet<ConstraintAnchor> hashSet = this.mTarget.mDependents;
        if (hashSet != null) {
            hashSet.add(this);
        }
        this.mMargin = i;
        this.mGoneMargin = i2;
        return true;
    }

    public final void findDependents(int i, WidgetGroup widgetGroup, ArrayList arrayList) {
        HashSet<ConstraintAnchor> hashSet = this.mDependents;
        if (hashSet != null) {
            Iterator<ConstraintAnchor> it = hashSet.iterator();
            while (it.hasNext()) {
                Grouping.findDependents(it.next().mOwner, i, arrayList, widgetGroup);
            }
        }
    }

    public final HashSet<ConstraintAnchor> getDependents() {
        return this.mDependents;
    }

    public final int getFinalValue() {
        if (this.mHasFinalValue) {
            return this.mFinalValue;
        }
        return 0;
    }

    public final int getMargin() {
        ConstraintAnchor constraintAnchor;
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        return (this.mGoneMargin == Integer.MIN_VALUE || (constraintAnchor = this.mTarget) == null || constraintAnchor.mOwner.getVisibility() != 8) ? this.mMargin : this.mGoneMargin;
    }

    public final ConstraintAnchor getOpposite() {
        Type type = this.mType;
        int ordinal = type.ordinal();
        ConstraintWidget constraintWidget = this.mOwner;
        switch (ordinal) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
                return null;
            case 1:
                return constraintWidget.mRight;
            case 2:
                return constraintWidget.mBottom;
            case 3:
                return constraintWidget.mLeft;
            case 4:
                return constraintWidget.mTop;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public final boolean hasCenteredDependents() {
        HashSet<ConstraintAnchor> hashSet = this.mDependents;
        if (hashSet == null) {
            return false;
        }
        Iterator<ConstraintAnchor> it = hashSet.iterator();
        while (it.hasNext()) {
            if (it.next().getOpposite().isConnected()) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDependents() {
        HashSet<ConstraintAnchor> hashSet = this.mDependents;
        return hashSet != null && hashSet.size() > 0;
    }

    public final boolean hasFinalValue() {
        return this.mHasFinalValue;
    }

    public final boolean isConnected() {
        return this.mTarget != null;
    }

    public final boolean isValidConnection(ConstraintAnchor constraintAnchor) {
        if (constraintAnchor == null) {
            return false;
        }
        Type type = Type.BASELINE;
        Type type2 = this.mType;
        ConstraintWidget constraintWidget = constraintAnchor.mOwner;
        Type type3 = constraintAnchor.mType;
        if (type3 == type2) {
            return type2 != type || (constraintWidget.hasBaseline() && this.mOwner.hasBaseline());
        }
        int ordinal = type2.ordinal();
        Type type4 = Type.CENTER_Y;
        Type type5 = Type.RIGHT;
        Type type6 = Type.CENTER_X;
        Type type7 = Type.LEFT;
        switch (ordinal) {
            case 0:
            case 7:
            case 8:
                return false;
            case 1:
            case 3:
                boolean z = type3 == type7 || type3 == type5;
                if (constraintWidget instanceof Guideline) {
                    return z || type3 == type6;
                }
                return z;
            case 2:
            case 4:
                boolean z2 = type3 == Type.TOP || type3 == Type.BOTTOM;
                if (constraintWidget instanceof Guideline) {
                    return z2 || type3 == type4;
                }
                return z2;
            case 5:
                return (type3 == type7 || type3 == type5) ? false : true;
            case 6:
                return (type3 == type || type3 == type6 || type3 == type4) ? false : true;
            default:
                throw new AssertionError(type2.name());
        }
    }

    public final void reset() {
        HashSet<ConstraintAnchor> hashSet;
        ConstraintAnchor constraintAnchor = this.mTarget;
        if (constraintAnchor != null && (hashSet = constraintAnchor.mDependents) != null) {
            hashSet.remove(this);
            if (this.mTarget.mDependents.size() == 0) {
                this.mTarget.mDependents = null;
            }
        }
        this.mDependents = null;
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = Integer.MIN_VALUE;
        this.mHasFinalValue = false;
        this.mFinalValue = 0;
    }

    public final void resetFinalResolution() {
        this.mHasFinalValue = false;
        this.mFinalValue = 0;
    }

    public final void resetSolverVariable() {
        SolverVariable solverVariable = this.mSolverVariable;
        if (solverVariable == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
        } else {
            solverVariable.reset();
        }
    }

    public final void setFinalValue(int i) {
        this.mFinalValue = i;
        this.mHasFinalValue = true;
    }

    public final String toString() {
        return this.mOwner.getDebugName() + ":" + this.mType.toString();
    }

    public final void connect(ConstraintAnchor constraintAnchor, int i) {
        connect(constraintAnchor, i, Integer.MIN_VALUE, false);
    }
}
