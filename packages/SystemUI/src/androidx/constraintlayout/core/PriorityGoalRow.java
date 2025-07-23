package androidx.constraintlayout.core;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PriorityGoalRow extends ArrayRow {
    public final GoalVariableAccessor mAccessor;
    public SolverVariable[] mArrayGoals;
    public int mNumGoals;
    public SolverVariable[] mSortArray;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class GoalVariableAccessor {
        public SolverVariable mVariable;

        public GoalVariableAccessor(PriorityGoalRow priorityGoalRow) {
        }

        public final String toString() {
            String str = "[ ";
            if (this.mVariable != null) {
                for (int i = 0; i < 9; i++) {
                    str = DpCornerSize$$ExternalSyntheticOutline0.m(this.mVariable.mGoalStrengthVector[i], " ", PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str));
                }
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "] ");
            m.append(this.mVariable);
            return m.toString();
        }
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.mArrayGoals = new SolverVariable[128];
        this.mSortArray = new SolverVariable[128];
        this.mNumGoals = 0;
        this.mAccessor = new GoalVariableAccessor(this);
    }

    public final void addToGoal(SolverVariable solverVariable) {
        int i;
        int i2 = this.mNumGoals + 1;
        SolverVariable[] solverVariableArr = this.mArrayGoals;
        if (i2 > solverVariableArr.length) {
            SolverVariable[] solverVariableArr2 = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            this.mArrayGoals = solverVariableArr2;
            this.mSortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.mArrayGoals;
        int i3 = this.mNumGoals;
        solverVariableArr3[i3] = solverVariable;
        int i4 = i3 + 1;
        this.mNumGoals = i4;
        if (i4 > 1 && solverVariableArr3[i3].id > solverVariable.id) {
            int i5 = 0;
            while (true) {
                i = this.mNumGoals;
                if (i5 >= i) {
                    break;
                }
                this.mSortArray[i5] = this.mArrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.mSortArray, 0, i, new Comparator(this) { // from class: androidx.constraintlayout.core.PriorityGoalRow.1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((SolverVariable) obj).id - ((SolverVariable) obj2).id;
                }
            });
            for (int i6 = 0; i6 < this.mNumGoals; i6++) {
                this.mArrayGoals[i6] = this.mSortArray[i6];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final SolverVariable getPivotCandidate(boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.mNumGoals; i2++) {
            SolverVariable[] solverVariableArr = this.mArrayGoals;
            SolverVariable solverVariable = solverVariableArr[i2];
            if (!zArr[solverVariable.id]) {
                GoalVariableAccessor goalVariableAccessor = this.mAccessor;
                goalVariableAccessor.mVariable = solverVariable;
                int i3 = 8;
                if (i == -1) {
                    while (i3 >= 0) {
                        float f = goalVariableAccessor.mVariable.mGoalStrengthVector[i3];
                        if (f <= 0.0f) {
                            if (f < 0.0f) {
                                i = i2;
                                break;
                            }
                            i3--;
                        }
                    }
                } else {
                    SolverVariable solverVariable2 = solverVariableArr[i];
                    while (true) {
                        if (i3 >= 0) {
                            float f2 = solverVariable2.mGoalStrengthVector[i3];
                            float f3 = goalVariableAccessor.mVariable.mGoalStrengthVector[i3];
                            if (f3 == f2) {
                                i3--;
                            } else if (f3 >= f2) {
                            }
                        }
                    }
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.mArrayGoals[i];
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final boolean isEmpty() {
        return this.mNumGoals == 0;
    }

    public final void removeGoal(SolverVariable solverVariable) {
        int i = 0;
        while (i < this.mNumGoals) {
            if (this.mArrayGoals[i] == solverVariable) {
                while (true) {
                    int i2 = this.mNumGoals;
                    if (i >= i2 - 1) {
                        this.mNumGoals = i2 - 1;
                        solverVariable.inGoal = false;
                        return;
                    } else {
                        SolverVariable[] solverVariableArr = this.mArrayGoals;
                        int i3 = i + 1;
                        solverVariableArr[i] = solverVariableArr[i3];
                        i = i3;
                    }
                }
            } else {
                i++;
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final String toString() {
        String m = DpCornerSize$$ExternalSyntheticOutline0.m(this.mConstantValue, ") : ", new StringBuilder(" goal -> ("));
        for (int i = 0; i < this.mNumGoals; i++) {
            SolverVariable solverVariable = this.mArrayGoals[i];
            GoalVariableAccessor goalVariableAccessor = this.mAccessor;
            goalVariableAccessor.mVariable = solverVariable;
            m = m + goalVariableAccessor + " ";
        }
        return m;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        SolverVariable solverVariable = arrayRow.mVariable;
        if (solverVariable == null) {
            return;
        }
        ArrayLinkedVariables arrayLinkedVariables = arrayRow.variables;
        int currentSize = arrayLinkedVariables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayLinkedVariables.getVariable(i);
            float variableValue = arrayLinkedVariables.getVariableValue(i);
            GoalVariableAccessor goalVariableAccessor = this.mAccessor;
            goalVariableAccessor.mVariable = variable;
            if (variable.inGoal) {
                boolean z2 = true;
                for (int i2 = 0; i2 < 9; i2++) {
                    float[] fArr = goalVariableAccessor.mVariable.mGoalStrengthVector;
                    float f = (solverVariable.mGoalStrengthVector[i2] * variableValue) + fArr[i2];
                    fArr[i2] = f;
                    if (Math.abs(f) < 1.0E-4f) {
                        goalVariableAccessor.mVariable.mGoalStrengthVector[i2] = 0.0f;
                    } else {
                        z2 = false;
                    }
                }
                if (z2) {
                    PriorityGoalRow.this.removeGoal(goalVariableAccessor.mVariable);
                }
            } else {
                for (int i3 = 0; i3 < 9; i3++) {
                    float f2 = solverVariable.mGoalStrengthVector[i3];
                    if (f2 != 0.0f) {
                        float f3 = f2 * variableValue;
                        if (Math.abs(f3) < 1.0E-4f) {
                            f3 = 0.0f;
                        }
                        goalVariableAccessor.mVariable.mGoalStrengthVector[i3] = f3;
                    } else {
                        goalVariableAccessor.mVariable.mGoalStrengthVector[i3] = 0.0f;
                    }
                }
                addToGoal(variable);
            }
            this.mConstantValue = (arrayRow.mConstantValue * variableValue) + this.mConstantValue;
        }
        removeGoal(solverVariable);
    }
}
