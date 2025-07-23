package androidx.constraintlayout.core;

import androidx.constraintlayout.core.SolverVariable;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ArrayRow {
    public final ArrayLinkedVariables variables;
    public SolverVariable mVariable = null;
    public float mConstantValue = 0.0f;
    public final ArrayList mVariablesToUpdate = new ArrayList();
    public boolean mIsSimpleDefinition = false;

    public ArrayRow() {
    }

    public final void addError(LinearSystem linearSystem, int i) {
        this.variables.put(linearSystem.createErrorVariable(i, "ep"), 1.0f);
        this.variables.put(linearSystem.createErrorVariable(i, "em"), -1.0f);
    }

    public final void createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.mConstantValue = i;
        }
        if (z) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
        }
    }

    public final void createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.mConstantValue = i;
        }
        if (z) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
        } else {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        }
    }

    public SolverVariable getPivotCandidate(boolean[] zArr) {
        return pickPivotInVariables(zArr, null);
    }

    public boolean isEmpty() {
        return this.mVariable == null && this.mConstantValue == 0.0f && this.variables.getCurrentSize() == 0;
    }

    public final SolverVariable pickPivotInVariables(boolean[] zArr, SolverVariable solverVariable) {
        SolverVariable.Type type;
        int currentSize = this.variables.getCurrentSize();
        SolverVariable solverVariable2 = null;
        float f = 0.0f;
        for (int i = 0; i < currentSize; i++) {
            float variableValue = this.variables.getVariableValue(i);
            if (variableValue < 0.0f) {
                SolverVariable variable = this.variables.getVariable(i);
                if ((zArr == null || !zArr[variable.id]) && variable != solverVariable && (((type = variable.mType) == SolverVariable.Type.SLACK || type == SolverVariable.Type.ERROR) && variableValue < f)) {
                    f = variableValue;
                    solverVariable2 = variable;
                }
            }
        }
        return solverVariable2;
    }

    public final void pivot(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.mVariable;
        if (solverVariable2 != null) {
            this.variables.put(solverVariable2, -1.0f);
            this.mVariable.mDefinitionId = -1;
            this.mVariable = null;
        }
        float remove = this.variables.remove(solverVariable, true) * (-1.0f);
        this.mVariable = solverVariable;
        if (remove == 1.0f) {
            return;
        }
        this.mConstantValue /= remove;
        ArrayLinkedVariables arrayLinkedVariables = this.variables;
        int i = arrayLinkedVariables.mHead;
        for (int i2 = 0; i != -1 && i2 < arrayLinkedVariables.mCurrentSize; i2++) {
            float[] fArr = arrayLinkedVariables.mArrayValues;
            fArr[i] = fArr[i] / remove;
            i = arrayLinkedVariables.mArrayNextIndices[i];
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r10 = this;
            androidx.constraintlayout.core.SolverVariable r0 = r10.mVariable
            if (r0 != 0) goto L7
            java.lang.String r0 = "0"
            goto L17
        L7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = ""
            r0.<init>(r1)
            androidx.constraintlayout.core.SolverVariable r1 = r10.mVariable
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L17:
            java.lang.String r1 = " = "
            java.lang.String r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r1)
            float r1 = r10.mConstantValue
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L35
            java.lang.StringBuilder r0 = androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(r0)
            float r1 = r10.mConstantValue
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = r3
            goto L36
        L35:
            r1 = r4
        L36:
            androidx.constraintlayout.core.ArrayLinkedVariables r5 = r10.variables
            int r5 = r5.getCurrentSize()
        L3c:
            if (r4 >= r5) goto L9c
            androidx.constraintlayout.core.ArrayLinkedVariables r6 = r10.variables
            androidx.constraintlayout.core.SolverVariable r6 = r6.getVariable(r4)
            if (r6 != 0) goto L47
            goto L99
        L47:
            androidx.constraintlayout.core.ArrayLinkedVariables r7 = r10.variables
            float r7 = r7.getVariableValue(r4)
            int r8 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r8 != 0) goto L52
            goto L99
        L52:
            java.lang.String r6 = r6.toString()
            r9 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r1 != 0) goto L66
            int r1 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r1 >= 0) goto L76
            java.lang.String r1 = "- "
            java.lang.String r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r1)
        L64:
            float r7 = r7 * r9
            goto L76
        L66:
            if (r8 <= 0) goto L6f
            java.lang.String r1 = " + "
            java.lang.String r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r1)
            goto L76
        L6f:
            java.lang.String r1 = " - "
            java.lang.String r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r1)
            goto L64
        L76:
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r1 != 0) goto L81
            java.lang.String r0 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r6)
            goto L98
        L81:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r7)
            java.lang.String r0 = " "
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
        L98:
            r1 = r3
        L99:
            int r4 = r4 + 1
            goto L3c
        L9c:
            if (r1 != 0) goto La5
            java.lang.String r10 = "0.0"
            java.lang.String r10 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r10)
            return r10
        La5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.ArrayRow.toString():java.lang.String");
    }

    public final void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z) {
        if (solverVariable == null || !solverVariable.isFinalValue) {
            return;
        }
        float f = this.variables.get(solverVariable);
        this.mConstantValue = (solverVariable.computedValue * f) + this.mConstantValue;
        this.variables.remove(solverVariable, z);
        if (z) {
            solverVariable.removeFromRow(this);
        }
        if (this.variables.getCurrentSize() == 0) {
            this.mIsSimpleDefinition = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        ArrayLinkedVariables arrayLinkedVariables = this.variables;
        arrayLinkedVariables.getClass();
        float f = arrayLinkedVariables.get(arrayRow.mVariable);
        arrayLinkedVariables.remove(arrayRow.mVariable, z);
        ArrayLinkedVariables arrayLinkedVariables2 = arrayRow.variables;
        int currentSize = arrayLinkedVariables2.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayLinkedVariables2.getVariable(i);
            arrayLinkedVariables.add(variable, arrayLinkedVariables2.get(variable) * f, z);
        }
        this.mConstantValue = (arrayRow.mConstantValue * f) + this.mConstantValue;
        if (z) {
            arrayRow.mVariable.removeFromRow(this);
        }
        if (this.mVariable == null || this.variables.getCurrentSize() != 0) {
            return;
        }
        this.mIsSimpleDefinition = true;
        linearSystem.hasSimpleDefinition = true;
    }

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }
}
