package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConstraintAnchor {
    public int mFinalValue;
    public boolean mHasFinalValue;
    public final ConstraintWidget mOwner;
    public SolverVariable mSolverVariable;
    public ConstraintAnchor mTarget;
    public final Type mType;
    public HashSet mDependents = null;
    public int mMargin = 0;
    public int mGoneMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.core.widgets.ConstraintAnchor$1 */
    public abstract /* synthetic */ class AbstractC01161 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        public static final /* synthetic */ int[] f14x6930e354;

        static {
            int[] iArr = new int[Type.values().length];
            f14x6930e354 = iArr;
            try {
                iArr[Type.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14x6930e354[Type.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f14x6930e354[Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f14x6930e354[Type.TOP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f14x6930e354[Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f14x6930e354[Type.BASELINE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f14x6930e354[Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f14x6930e354[Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f14x6930e354[Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
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
            constraintAnchor.mDependents = new HashSet();
        }
        HashSet hashSet = this.mTarget.mDependents;
        if (hashSet != null) {
            hashSet.add(this);
        }
        this.mMargin = i;
        this.mGoneMargin = i2;
        return true;
    }

    public final void findDependents(int i, WidgetGroup widgetGroup, ArrayList arrayList) {
        HashSet hashSet = this.mDependents;
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                Grouping.findDependents(((ConstraintAnchor) it.next()).mOwner, i, arrayList, widgetGroup);
            }
        }
    }

    public final int getFinalValue() {
        if (this.mHasFinalValue) {
            return this.mFinalValue;
        }
        return 0;
    }

    public final int getMargin() {
        ConstraintAnchor constraintAnchor;
        if (this.mOwner.mVisibility == 8) {
            return 0;
        }
        int i = this.mGoneMargin;
        return (i == Integer.MIN_VALUE || (constraintAnchor = this.mTarget) == null || constraintAnchor.mOwner.mVisibility != 8) ? this.mMargin : i;
    }

    public final ConstraintAnchor getOpposite() {
        int[] iArr = AbstractC01161.f14x6930e354;
        Type type = this.mType;
        int i = iArr[type.ordinal()];
        ConstraintWidget constraintWidget = this.mOwner;
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 8:
            case 9:
                return null;
            case 2:
                return constraintWidget.mRight;
            case 3:
                return constraintWidget.mLeft;
            case 4:
                return constraintWidget.mBottom;
            case 5:
                return constraintWidget.mTop;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final boolean hasCenteredDependents() {
        HashSet hashSet = this.mDependents;
        if (hashSet == null) {
            return false;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((ConstraintAnchor) it.next()).getOpposite().isConnected()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isConnected() {
        return this.mTarget != null;
    }

    public final boolean isValidConnection(ConstraintAnchor constraintAnchor) {
        if (constraintAnchor == null) {
            return false;
        }
        Type type = this.mType;
        ConstraintWidget constraintWidget = constraintAnchor.mOwner;
        Type type2 = constraintAnchor.mType;
        if (type2 == type) {
            return type != Type.BASELINE || (constraintWidget.hasBaseline && this.mOwner.hasBaseline);
        }
        switch (AbstractC01161.f14x6930e354[type.ordinal()]) {
            case 1:
                return (type2 == Type.BASELINE || type2 == Type.CENTER_X || type2 == Type.CENTER_Y) ? false : true;
            case 2:
            case 3:
                boolean z = type2 == Type.LEFT || type2 == Type.RIGHT;
                if (constraintWidget instanceof Guideline) {
                    return z || type2 == Type.CENTER_X;
                }
                return z;
            case 4:
            case 5:
                boolean z2 = type2 == Type.TOP || type2 == Type.BOTTOM;
                if (constraintWidget instanceof Guideline) {
                    return z2 || type2 == Type.CENTER_Y;
                }
                return z2;
            case 6:
                return (type2 == Type.LEFT || type2 == Type.RIGHT) ? false : true;
            case 7:
            case 8:
            case 9:
                return false;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final void reset() {
        HashSet hashSet;
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
        this.mGoneMargin = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mHasFinalValue = false;
        this.mFinalValue = 0;
    }

    public final void resetSolverVariable() {
        SolverVariable solverVariable = this.mSolverVariable;
        if (solverVariable == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED, (String) null);
        } else {
            solverVariable.reset();
        }
    }

    public final void setFinalValue(int i) {
        this.mFinalValue = i;
        this.mHasFinalValue = true;
    }

    public final String toString() {
        return this.mOwner.mDebugName + ":" + this.mType.toString();
    }

    public final void connect(ConstraintAnchor constraintAnchor, int i) {
        connect(constraintAnchor, i, VideoPlayer.MEDIA_ERROR_SYSTEM, false);
    }
}
