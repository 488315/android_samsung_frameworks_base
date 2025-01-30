package androidx.constraintlayout.core;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.SolverVariable;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ArrayRow {
    public ArrayRowVariables variables;
    public SolverVariable variable = null;
    public float constantValue = 0.0f;
    public final ArrayList variablesToUpdate = new ArrayList();
    public boolean isSimpleDefinition = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ArrayRowVariables {
        void add(SolverVariable solverVariable, float f, boolean z);

        void clear();

        boolean contains(SolverVariable solverVariable);

        void divideByAmount(float f);

        float get(SolverVariable solverVariable);

        int getCurrentSize();

        SolverVariable getVariable(int i);

        float getVariableValue(int i);

        void invert();

        void put(SolverVariable solverVariable, float f);

        float remove(SolverVariable solverVariable, boolean z);

        float use(ArrayRow arrayRow, boolean z);
    }

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
            this.constantValue = i;
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
            this.constantValue = i;
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
        return this.variable == null && this.constantValue == 0.0f && this.variables.getCurrentSize() == 0;
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
                if ((zArr == null || !zArr[variable.f2id]) && variable != solverVariable && (((type = variable.mType) == SolverVariable.Type.SLACK || type == SolverVariable.Type.ERROR) && variableValue < f)) {
                    f = variableValue;
                    solverVariable2 = variable;
                }
            }
        }
        return solverVariable2;
    }

    public final void pivot(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.variable;
        if (solverVariable2 != null) {
            this.variables.put(solverVariable2, -1.0f);
            this.variable.definitionId = -1;
            this.variable = null;
        }
        float remove = this.variables.remove(solverVariable, true) * (-1.0f);
        this.variable = solverVariable;
        if (remove == 1.0f) {
            return;
        }
        this.constantValue /= remove;
        this.variables.divideByAmount(remove);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String toString() {
        boolean z;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(this.variable == null ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : "" + this.variable, " = ");
        if (this.constantValue != 0.0f) {
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m14m);
            m18m.append(this.constantValue);
            m14m = m18m.toString();
            z = true;
        } else {
            z = false;
        }
        int currentSize = this.variables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = this.variables.getVariable(i);
            if (variable != null) {
                float variableValue = this.variables.getVariableValue(i);
                if (variableValue != 0.0f) {
                    String solverVariable = variable.toString();
                    if (!z) {
                        if (variableValue < 0.0f) {
                            m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "- ");
                            variableValue *= -1.0f;
                        }
                        m14m = variableValue == 1.0f ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, solverVariable) : m14m + variableValue + " " + solverVariable;
                        z = true;
                    } else if (variableValue > 0.0f) {
                        m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, " + ");
                        if (variableValue == 1.0f) {
                        }
                        z = true;
                    } else {
                        m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, " - ");
                        variableValue *= -1.0f;
                        if (variableValue == 1.0f) {
                        }
                        z = true;
                    }
                }
            }
        }
        return !z ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "0.0") : m14m;
    }

    public final void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z) {
        if (solverVariable == null || !solverVariable.isFinalValue) {
            return;
        }
        float f = this.variables.get(solverVariable);
        this.constantValue = (solverVariable.computedValue * f) + this.constantValue;
        this.variables.remove(solverVariable, z);
        if (z) {
            solverVariable.removeFromRow(this);
        }
        if (this.variables.getCurrentSize() == 0) {
            this.isSimpleDefinition = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        float use = this.variables.use(arrayRow, z);
        this.constantValue = (arrayRow.constantValue * use) + this.constantValue;
        if (z) {
            arrayRow.variable.removeFromRow(this);
        }
        if (this.variable == null || this.variables.getCurrentSize() != 0) {
            return;
        }
        this.isSimpleDefinition = true;
        linearSystem.hasSimpleDefinition = true;
    }

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }
}
