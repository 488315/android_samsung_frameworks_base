package androidx.constraintlayout.core;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PriorityGoalRow extends ArrayRow {
    public final GoalVariableAccessor accessor;
    public SolverVariable[] arrayGoals;
    public int numGoals;
    public SolverVariable[] sortArray;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GoalVariableAccessor {
        public SolverVariable variable;

        public GoalVariableAccessor(PriorityGoalRow priorityGoalRow) {
        }

        public final String toString() {
            String str = "[ ";
            if (this.variable != null) {
                for (int i = 0; i < 9; i++) {
                    StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                    m18m.append(this.variable.goalStrengthVector[i]);
                    m18m.append(" ");
                    str = m18m.toString();
                }
            }
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "] ");
            m2m.append(this.variable);
            return m2m.toString();
        }
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.arrayGoals = new SolverVariable[128];
        this.sortArray = new SolverVariable[128];
        this.numGoals = 0;
        this.accessor = new GoalVariableAccessor(this);
    }

    public final void addToGoal(SolverVariable solverVariable) {
        int i;
        int i2 = this.numGoals + 1;
        SolverVariable[] solverVariableArr = this.arrayGoals;
        if (i2 > solverVariableArr.length) {
            SolverVariable[] solverVariableArr2 = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            this.arrayGoals = solverVariableArr2;
            this.sortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.arrayGoals;
        int i3 = this.numGoals;
        solverVariableArr3[i3] = solverVariable;
        int i4 = i3 + 1;
        this.numGoals = i4;
        if (i4 > 1 && solverVariableArr3[i4 - 1].f2id > solverVariable.f2id) {
            int i5 = 0;
            while (true) {
                i = this.numGoals;
                if (i5 >= i) {
                    break;
                }
                this.sortArray[i5] = this.arrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.sortArray, 0, i, new Comparator(this) { // from class: androidx.constraintlayout.core.PriorityGoalRow.1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((SolverVariable) obj).f2id - ((SolverVariable) obj2).f2id;
                }
            });
            for (int i6 = 0; i6 < this.numGoals; i6++) {
                this.arrayGoals[i6] = this.sortArray[i6];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x004d, code lost:
    
        if (r9 < r8) goto L30;
     */
    @Override // androidx.constraintlayout.core.ArrayRow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SolverVariable getPivotCandidate(boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.numGoals; i2++) {
            SolverVariable[] solverVariableArr = this.arrayGoals;
            SolverVariable solverVariable = solverVariableArr[i2];
            if (!zArr[solverVariable.f2id]) {
                GoalVariableAccessor goalVariableAccessor = this.accessor;
                goalVariableAccessor.variable = solverVariable;
                boolean z = true;
                int i3 = 8;
                if (i == -1) {
                    while (i3 >= 0) {
                        float f = goalVariableAccessor.variable.goalStrengthVector[i3];
                        if (f > 0.0f) {
                            break;
                        }
                        if (f < 0.0f) {
                            break;
                        }
                        i3--;
                    }
                    z = false;
                    if (!z) {
                    }
                    i = i2;
                } else {
                    SolverVariable solverVariable2 = solverVariableArr[i];
                    while (true) {
                        if (i3 < 0) {
                            break;
                        }
                        float f2 = solverVariable2.goalStrengthVector[i3];
                        float f3 = goalVariableAccessor.variable.goalStrengthVector[i3];
                        if (f3 == f2) {
                            i3--;
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                    i = i2;
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.arrayGoals[i];
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final boolean isEmpty() {
        return this.numGoals == 0;
    }

    public final void removeGoal(SolverVariable solverVariable) {
        int i = 0;
        while (i < this.numGoals) {
            if (this.arrayGoals[i] == solverVariable) {
                while (true) {
                    int i2 = this.numGoals;
                    if (i >= i2 - 1) {
                        this.numGoals = i2 - 1;
                        solverVariable.inGoal = false;
                        return;
                    } else {
                        SolverVariable[] solverVariableArr = this.arrayGoals;
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
        String str = " goal -> (" + this.constantValue + ") : ";
        for (int i = 0; i < this.numGoals; i++) {
            SolverVariable solverVariable = this.arrayGoals[i];
            GoalVariableAccessor goalVariableAccessor = this.accessor;
            goalVariableAccessor.variable = solverVariable;
            str = str + goalVariableAccessor + " ";
        }
        return str;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        SolverVariable solverVariable = arrayRow.variable;
        if (solverVariable == null) {
            return;
        }
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayRowVariables.getVariable(i);
            float variableValue = arrayRowVariables.getVariableValue(i);
            GoalVariableAccessor goalVariableAccessor = this.accessor;
            goalVariableAccessor.variable = variable;
            boolean z2 = true;
            if (variable.inGoal) {
                for (int i2 = 0; i2 < 9; i2++) {
                    float[] fArr = goalVariableAccessor.variable.goalStrengthVector;
                    float f = (solverVariable.goalStrengthVector[i2] * variableValue) + fArr[i2];
                    fArr[i2] = f;
                    if (Math.abs(f) < 1.0E-4f) {
                        goalVariableAccessor.variable.goalStrengthVector[i2] = 0.0f;
                    } else {
                        z2 = false;
                    }
                }
                if (z2) {
                    PriorityGoalRow.this.removeGoal(goalVariableAccessor.variable);
                }
                z2 = false;
            } else {
                for (int i3 = 0; i3 < 9; i3++) {
                    float f2 = solverVariable.goalStrengthVector[i3];
                    if (f2 != 0.0f) {
                        float f3 = f2 * variableValue;
                        if (Math.abs(f3) < 1.0E-4f) {
                            f3 = 0.0f;
                        }
                        goalVariableAccessor.variable.goalStrengthVector[i3] = f3;
                    } else {
                        goalVariableAccessor.variable.goalStrengthVector[i3] = 0.0f;
                    }
                }
            }
            if (z2) {
                addToGoal(variable);
            }
            this.constantValue = (arrayRow.constantValue * variableValue) + this.constantValue;
        }
        removeGoal(solverVariable);
    }
}
