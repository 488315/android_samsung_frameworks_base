package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes.dex */
public final class PriorityGoalRow extends ArrayRow {
    GoalVariableAccessor mAccessor;
    private SolverVariable[] mArrayGoals;
    private int mNumGoals;
    private SolverVariable[] mSortArray;

    /* renamed from: androidx.constraintlayout.core.PriorityGoalRow$1 */
    final class C00761 implements Comparator<SolverVariable> {
        @Override // java.util.Comparator
        public final int compare(SolverVariable solverVariable, SolverVariable solverVariable2) {
            return solverVariable.f1id - solverVariable2.f1id;
        }
    }

    class GoalVariableAccessor {
        SolverVariable mVariable;

        GoalVariableAccessor() {
        }

        public final String toString() {
            String str = "[ ";
            if (this.mVariable != null) {
                for (int i = 0; i < 9; i++) {
                    StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str);
                    m2m.append(this.mVariable.mGoalStrengthVector[i]);
                    m2m.append(" ");
                    str = m2m.toString();
                }
            }
            return str + "] " + this.mVariable;
        }
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.mArrayGoals = new SolverVariable[128];
        this.mSortArray = new SolverVariable[128];
        this.mNumGoals = 0;
        this.mAccessor = new GoalVariableAccessor();
    }

    private void addToGoal(SolverVariable solverVariable) {
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
        if (i4 > 1 && solverVariableArr3[i4 - 1].f1id > solverVariable.f1id) {
            int i5 = 0;
            while (true) {
                i = this.mNumGoals;
                if (i5 >= i) {
                    break;
                }
                this.mSortArray[i5] = this.mArrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.mSortArray, 0, i, new C00761());
            for (int i6 = 0; i6 < this.mNumGoals; i6++) {
                this.mArrayGoals[i6] = this.mSortArray[i6];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeGoal(SolverVariable solverVariable) {
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

    public final void addError(SolverVariable solverVariable) {
        this.mAccessor.mVariable = solverVariable;
        Arrays.fill(solverVariable.mGoalStrengthVector, 0.0f);
        solverVariable.mGoalStrengthVector[solverVariable.strength] = 1.0f;
        addToGoal(solverVariable);
    }

    public final void clear() {
        this.mNumGoals = 0;
        this.mConstantValue = 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x004d, code lost:
    
        if (r9 < r8) goto L30;
     */
    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SolverVariable getPivotCandidate(boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.mNumGoals; i2++) {
            SolverVariable[] solverVariableArr = this.mArrayGoals;
            SolverVariable solverVariable = solverVariableArr[i2];
            if (!zArr[solverVariable.f1id]) {
                GoalVariableAccessor goalVariableAccessor = this.mAccessor;
                goalVariableAccessor.mVariable = solverVariable;
                boolean z = true;
                int i3 = 8;
                if (i == -1) {
                    while (i3 >= 0) {
                        float f = goalVariableAccessor.mVariable.mGoalStrengthVector[i3];
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
                        float f2 = solverVariable2.mGoalStrengthVector[i3];
                        float f3 = goalVariableAccessor.mVariable.mGoalStrengthVector[i3];
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
        return this.mArrayGoals[i];
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final boolean isEmpty() {
        return this.mNumGoals == 0;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final String toString() {
        String str = " goal -> (" + this.mConstantValue + ") : ";
        for (int i = 0; i < this.mNumGoals; i++) {
            this.mAccessor.mVariable = this.mArrayGoals[i];
            StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str);
            m2m.append(this.mAccessor);
            m2m.append(" ");
            str = m2m.toString();
        }
        return str;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public final void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        SolverVariable solverVariable = arrayRow.mVariable;
        if (solverVariable == null) {
            return;
        }
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayRowVariables.getVariable(i);
            float variableValue = arrayRowVariables.getVariableValue(i);
            GoalVariableAccessor goalVariableAccessor = this.mAccessor;
            goalVariableAccessor.mVariable = variable;
            boolean z2 = true;
            if (variable.inGoal) {
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
                z2 = false;
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
            }
            if (z2) {
                addToGoal(variable);
            }
            this.mConstantValue = (arrayRow.mConstantValue * variableValue) + this.mConstantValue;
        }
        removeGoal(solverVariable);
    }
}
