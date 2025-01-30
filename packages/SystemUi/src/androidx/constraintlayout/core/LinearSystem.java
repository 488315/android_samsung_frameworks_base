package androidx.constraintlayout.core;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LinearSystem {
    public static int POOL_SIZE = 1000;
    public static boolean USE_DEPENDENCY_ORDERING = false;
    public final Cache mCache;
    public final PriorityGoalRow mGoal;
    public ArrayRow[] mRows;
    public ArrayRow mTempGoal;
    public boolean hasSimpleDefinition = false;
    public int mVariablesID = 0;
    public int TABLE_SIZE = 32;
    public int mMaxColumns = 32;
    public boolean newgraphOptimizer = false;
    public boolean[] mAlreadyTestedCandidates = new boolean[32];
    public int mNumColumns = 1;
    public int mNumRows = 0;
    public int mMaxRows = 32;
    public SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
    public int mPoolVariablesCount = 0;

    public LinearSystem() {
        this.mRows = null;
        this.mRows = new ArrayRow[32];
        for (int i = 0; i < this.mNumRows; i++) {
            ArrayRow[] arrayRowArr = this.mRows;
            ArrayRow arrayRow = arrayRowArr[i];
            if (arrayRow != null) {
                Pools$SimplePool pools$SimplePool = this.mCache.arrayRowPool;
                int i2 = pools$SimplePool.mPoolSize;
                Object[] objArr = pools$SimplePool.mPool;
                if (i2 < objArr.length) {
                    objArr[i2] = arrayRow;
                    pools$SimplePool.mPoolSize = i2 + 1;
                }
            }
            arrayRowArr[i] = null;
        }
        Cache cache = new Cache();
        this.mCache = cache;
        this.mGoal = new PriorityGoalRow(cache);
        this.mTempGoal = new ArrayRow(cache);
    }

    public static int getObjectVariableValue(ConstraintAnchor constraintAnchor) {
        SolverVariable solverVariable = constraintAnchor.mSolverVariable;
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v0 */
    public final SolverVariable acquireSolverVariable(SolverVariable.Type type, String str) {
        Pools$SimplePool pools$SimplePool = this.mCache.solverVariablePool;
        int i = pools$SimplePool.mPoolSize;
        SolverVariable solverVariable = null;
        if (i > 0) {
            int i2 = i - 1;
            ?? r3 = pools$SimplePool.mPool;
            ?? r4 = r3[i2];
            r3[i2] = 0;
            pools$SimplePool.mPoolSize = i2;
            solverVariable = r4;
        }
        SolverVariable solverVariable2 = solverVariable;
        if (solverVariable2 == null) {
            solverVariable2 = new SolverVariable(type, str);
            solverVariable2.mType = type;
        } else {
            solverVariable2.reset();
            solverVariable2.mType = type;
        }
        int i3 = this.mPoolVariablesCount;
        int i4 = POOL_SIZE;
        if (i3 >= i4) {
            int i5 = i4 * 2;
            POOL_SIZE = i5;
            this.mPoolVariables = (SolverVariable[]) Arrays.copyOf(this.mPoolVariables, i5);
        }
        SolverVariable[] solverVariableArr = this.mPoolVariables;
        int i6 = this.mPoolVariablesCount;
        this.mPoolVariablesCount = i6 + 1;
        solverVariableArr[i6] = solverVariable2;
        return solverVariable2;
    }

    public final void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2, int i3) {
        ArrayRow createRow = createRow();
        if (solverVariable2 == solverVariable3) {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable4, 1.0f);
            createRow.variables.put(solverVariable2, -2.0f);
        } else if (f == 0.5f) {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable2, -1.0f);
            createRow.variables.put(solverVariable3, -1.0f);
            createRow.variables.put(solverVariable4, 1.0f);
            if (i > 0 || i2 > 0) {
                createRow.constantValue = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            createRow.variables.put(solverVariable, -1.0f);
            createRow.variables.put(solverVariable2, 1.0f);
            createRow.constantValue = i;
        } else if (f >= 1.0f) {
            createRow.variables.put(solverVariable4, -1.0f);
            createRow.variables.put(solverVariable3, 1.0f);
            createRow.constantValue = -i2;
        } else {
            float f2 = 1.0f - f;
            createRow.variables.put(solverVariable, f2 * 1.0f);
            createRow.variables.put(solverVariable2, f2 * (-1.0f));
            createRow.variables.put(solverVariable3, (-1.0f) * f);
            createRow.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                createRow.constantValue = (i2 * f) + ((-i) * f2);
            }
        }
        if (i3 != 8) {
            createRow.addError(this, i3);
        }
        addConstraint(createRow);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ba, code lost:
    
        if (r4.usageInRowCount <= 1) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c6, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c3, code lost:
    
        if (r4.usageInRowCount <= 1) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00e1, code lost:
    
        if (r4.usageInRowCount <= 1) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00ed, code lost:
    
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00ea, code lost:
    
        if (r4.usageInRowCount <= 1) goto L87;
     */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addConstraint(ArrayRow arrayRow) {
        boolean z;
        boolean z2;
        boolean z3;
        SolverVariable solverVariable;
        SolverVariable pickPivotInVariables;
        ArrayList arrayList;
        boolean z4 = true;
        if (this.mNumRows + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (arrayRow.isSimpleDefinition) {
            z = false;
        } else {
            if (this.mRows.length != 0) {
                boolean z5 = false;
                while (!z5) {
                    int currentSize = arrayRow.variables.getCurrentSize();
                    int i = 0;
                    while (true) {
                        arrayList = arrayRow.variablesToUpdate;
                        if (i >= currentSize) {
                            break;
                        }
                        SolverVariable variable = arrayRow.variables.getVariable(i);
                        if (variable.definitionId != -1 || variable.isFinalValue) {
                            arrayList.add(variable);
                        }
                        i++;
                    }
                    int size = arrayList.size();
                    if (size > 0) {
                        for (int i2 = 0; i2 < size; i2++) {
                            SolverVariable solverVariable2 = (SolverVariable) arrayList.get(i2);
                            if (solverVariable2.isFinalValue) {
                                arrayRow.updateFromFinalVariable(this, solverVariable2, true);
                            } else {
                                arrayRow.updateFromRow(this, this.mRows[solverVariable2.definitionId], true);
                            }
                        }
                        arrayList.clear();
                    } else {
                        z5 = true;
                    }
                }
                if (arrayRow.variable != null && arrayRow.variables.getCurrentSize() == 0) {
                    arrayRow.isSimpleDefinition = true;
                    this.hasSimpleDefinition = true;
                }
            }
            if (arrayRow.isEmpty()) {
                return;
            }
            float f = arrayRow.constantValue;
            if (f < 0.0f) {
                arrayRow.constantValue = f * (-1.0f);
                arrayRow.variables.invert();
            }
            int currentSize2 = arrayRow.variables.getCurrentSize();
            float f2 = 0.0f;
            float f3 = 0.0f;
            SolverVariable solverVariable3 = null;
            SolverVariable solverVariable4 = null;
            boolean z6 = false;
            boolean z7 = false;
            for (int i3 = 0; i3 < currentSize2; i3++) {
                float variableValue = arrayRow.variables.getVariableValue(i3);
                SolverVariable variable2 = arrayRow.variables.getVariable(i3);
                if (variable2.mType == SolverVariable.Type.UNRESTRICTED) {
                    if (solverVariable3 != null) {
                        if (f2 <= variableValue) {
                            if (!z6) {
                                if (!(variable2.usageInRowCount <= 1)) {
                                }
                            }
                        }
                        z6 = true;
                    }
                    solverVariable3 = variable2;
                    f2 = variableValue;
                } else if (solverVariable3 == null && variableValue < 0.0f) {
                    if (solverVariable4 != null) {
                        if (f3 <= variableValue) {
                            if (!z7) {
                                if (!(variable2.usageInRowCount <= 1)) {
                                }
                            }
                        }
                        z7 = true;
                    }
                    solverVariable4 = variable2;
                    f3 = variableValue;
                }
            }
            if (solverVariable3 == null) {
                solverVariable3 = solverVariable4;
            }
            if (solverVariable3 == null) {
                z2 = true;
            } else {
                arrayRow.pivot(solverVariable3);
                z2 = false;
            }
            if (arrayRow.variables.getCurrentSize() == 0) {
                arrayRow.isSimpleDefinition = true;
            }
            if (z2) {
                if (this.mNumColumns + 1 >= this.mMaxColumns) {
                    increaseTableSize();
                }
                SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
                int i4 = this.mVariablesID + 1;
                this.mVariablesID = i4;
                this.mNumColumns++;
                acquireSolverVariable.f2id = i4;
                Cache cache = this.mCache;
                cache.mIndexedVariables[i4] = acquireSolverVariable;
                arrayRow.variable = acquireSolverVariable;
                int i5 = this.mNumRows;
                addRow(arrayRow);
                if (this.mNumRows == i5 + 1) {
                    ArrayRow arrayRow2 = this.mTempGoal;
                    arrayRow2.getClass();
                    arrayRow2.variable = null;
                    arrayRow2.variables.clear();
                    for (int i6 = 0; i6 < arrayRow.variables.getCurrentSize(); i6++) {
                        arrayRow2.variables.add(arrayRow.variables.getVariable(i6), arrayRow.variables.getVariableValue(i6), true);
                    }
                    optimize(this.mTempGoal);
                    if (acquireSolverVariable.definitionId == -1) {
                        if (arrayRow.variable == acquireSolverVariable && (pickPivotInVariables = arrayRow.pickPivotInVariables(null, acquireSolverVariable)) != null) {
                            arrayRow.pivot(pickPivotInVariables);
                        }
                        if (!arrayRow.isSimpleDefinition) {
                            arrayRow.variable.updateReferencesWithNewDefinition(this, arrayRow);
                        }
                        Pools$SimplePool pools$SimplePool = cache.arrayRowPool;
                        int i7 = pools$SimplePool.mPoolSize;
                        Object[] objArr = pools$SimplePool.mPool;
                        if (i7 < objArr.length) {
                            objArr[i7] = arrayRow;
                            pools$SimplePool.mPoolSize = i7 + 1;
                        }
                        this.mNumRows--;
                    }
                    z3 = true;
                    solverVariable = arrayRow.variable;
                    if (solverVariable != null || (solverVariable.mType != SolverVariable.Type.UNRESTRICTED && arrayRow.constantValue < 0.0f)) {
                        z4 = false;
                    }
                    if (z4) {
                        return;
                    } else {
                        z = z3;
                    }
                }
            }
            z3 = false;
            solverVariable = arrayRow.variable;
            if (solverVariable != null) {
            }
            z4 = false;
            if (z4) {
            }
        }
        if (z) {
            return;
        }
        addRow(arrayRow);
    }

    public final void addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        if (i2 == 8 && solverVariable2.isFinalValue && solverVariable.definitionId == -1) {
            solverVariable.setFinalValue(this, solverVariable2.computedValue + i);
            return;
        }
        ArrayRow createRow = createRow();
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            createRow.constantValue = i;
        }
        if (z) {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable2, -1.0f);
        } else {
            createRow.variables.put(solverVariable, -1.0f);
            createRow.variables.put(solverVariable2, 1.0f);
        }
        if (i2 != 8) {
            createRow.addError(this, i2);
        }
        addConstraint(createRow);
    }

    public final void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 8) {
            createRow.variables.put(createErrorVariable(i2, null), (int) (createRow.variables.get(createSlackVariable) * (-1.0f)));
        }
        addConstraint(createRow);
    }

    public final void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 8) {
            createRow.variables.put(createErrorVariable(i2, null), (int) (createRow.variables.get(createSlackVariable) * (-1.0f)));
        }
        addConstraint(createRow);
    }

    public final void addRow(ArrayRow arrayRow) {
        int i;
        if (arrayRow.isSimpleDefinition) {
            arrayRow.variable.setFinalValue(this, arrayRow.constantValue);
        } else {
            ArrayRow[] arrayRowArr = this.mRows;
            int i2 = this.mNumRows;
            arrayRowArr[i2] = arrayRow;
            SolverVariable solverVariable = arrayRow.variable;
            solverVariable.definitionId = i2;
            this.mNumRows = i2 + 1;
            solverVariable.updateReferencesWithNewDefinition(this, arrayRow);
        }
        if (this.hasSimpleDefinition) {
            int i3 = 0;
            while (i3 < this.mNumRows) {
                if (this.mRows[i3] == null) {
                    System.out.println("WTF");
                }
                ArrayRow arrayRow2 = this.mRows[i3];
                if (arrayRow2 != null && arrayRow2.isSimpleDefinition) {
                    arrayRow2.variable.setFinalValue(this, arrayRow2.constantValue);
                    Pools$SimplePool pools$SimplePool = this.mCache.arrayRowPool;
                    int i4 = pools$SimplePool.mPoolSize;
                    Object[] objArr = pools$SimplePool.mPool;
                    if (i4 < objArr.length) {
                        objArr[i4] = arrayRow2;
                        pools$SimplePool.mPoolSize = i4 + 1;
                    }
                    this.mRows[i3] = null;
                    int i5 = i3 + 1;
                    int i6 = i5;
                    while (true) {
                        i = this.mNumRows;
                        if (i5 >= i) {
                            break;
                        }
                        ArrayRow[] arrayRowArr2 = this.mRows;
                        int i7 = i5 - 1;
                        ArrayRow arrayRow3 = arrayRowArr2[i5];
                        arrayRowArr2[i7] = arrayRow3;
                        SolverVariable solverVariable2 = arrayRow3.variable;
                        if (solverVariable2.definitionId == i5) {
                            solverVariable2.definitionId = i7;
                        }
                        i6 = i5;
                        i5++;
                    }
                    if (i6 < i) {
                        this.mRows[i6] = null;
                    }
                    this.mNumRows = i - 1;
                    i3--;
                }
                i3++;
            }
            this.hasSimpleDefinition = false;
        }
    }

    public final void computeValues() {
        for (int i = 0; i < this.mNumRows; i++) {
            ArrayRow arrayRow = this.mRows[i];
            arrayRow.variable.computedValue = arrayRow.constantValue;
        }
    }

    public final SolverVariable createErrorVariable(int i, String str) {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR, str);
        int i2 = this.mVariablesID + 1;
        this.mVariablesID = i2;
        this.mNumColumns++;
        acquireSolverVariable.f2id = i2;
        acquireSolverVariable.strength = i;
        this.mCache.mIndexedVariables[i2] = acquireSolverVariable;
        PriorityGoalRow priorityGoalRow = this.mGoal;
        priorityGoalRow.accessor.variable = acquireSolverVariable;
        Arrays.fill(acquireSolverVariable.goalStrengthVector, 0.0f);
        acquireSolverVariable.goalStrengthVector[acquireSolverVariable.strength] = 1.0f;
        priorityGoalRow.addToGoal(acquireSolverVariable);
        return acquireSolverVariable;
    }

    public final SolverVariable createObjectVariable(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.mSolverVariable;
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable();
                solverVariable = constraintAnchor.mSolverVariable;
            }
            int i = solverVariable.f2id;
            Cache cache = this.mCache;
            if (i == -1 || i > this.mVariablesID || cache.mIndexedVariables[i] == null) {
                if (i != -1) {
                    solverVariable.reset();
                }
                int i2 = this.mVariablesID + 1;
                this.mVariablesID = i2;
                this.mNumColumns++;
                solverVariable.f2id = i2;
                solverVariable.mType = SolverVariable.Type.UNRESTRICTED;
                cache.mIndexedVariables[i2] = solverVariable;
            }
        }
        return solverVariable;
    }

    public final ArrayRow createRow() {
        Object obj;
        Cache cache = this.mCache;
        Pools$SimplePool pools$SimplePool = cache.arrayRowPool;
        int i = pools$SimplePool.mPoolSize;
        if (i > 0) {
            int i2 = i - 1;
            Object[] objArr = pools$SimplePool.mPool;
            obj = objArr[i2];
            objArr[i2] = null;
            pools$SimplePool.mPoolSize = i2;
        } else {
            obj = null;
        }
        ArrayRow arrayRow = (ArrayRow) obj;
        if (arrayRow == null) {
            return new ArrayRow(cache);
        }
        arrayRow.variable = null;
        arrayRow.variables.clear();
        arrayRow.constantValue = 0.0f;
        arrayRow.isSimpleDefinition = false;
        return arrayRow;
    }

    public final SolverVariable createSlackVariable() {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
        int i = this.mVariablesID + 1;
        this.mVariablesID = i;
        this.mNumColumns++;
        acquireSolverVariable.f2id = i;
        this.mCache.mIndexedVariables[i] = acquireSolverVariable;
        return acquireSolverVariable;
    }

    public final void increaseTableSize() {
        int i = this.TABLE_SIZE * 2;
        this.TABLE_SIZE = i;
        this.mRows = (ArrayRow[]) Arrays.copyOf(this.mRows, i);
        Cache cache = this.mCache;
        cache.mIndexedVariables = (SolverVariable[]) Arrays.copyOf(cache.mIndexedVariables, this.TABLE_SIZE);
        int i2 = this.TABLE_SIZE;
        this.mAlreadyTestedCandidates = new boolean[i2];
        this.mMaxColumns = i2;
        this.mMaxRows = i2;
    }

    public final void minimize() {
        PriorityGoalRow priorityGoalRow = this.mGoal;
        if (priorityGoalRow.isEmpty()) {
            computeValues();
            return;
        }
        if (!this.newgraphOptimizer) {
            minimizeGoal(priorityGoalRow);
            return;
        }
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.mNumRows) {
                z = true;
                break;
            } else if (!this.mRows[i].isSimpleDefinition) {
                break;
            } else {
                i++;
            }
        }
        if (z) {
            computeValues();
        } else {
            minimizeGoal(priorityGoalRow);
        }
    }

    public final void minimizeGoal(PriorityGoalRow priorityGoalRow) {
        float f;
        int i;
        boolean z;
        int i2 = 0;
        while (true) {
            f = 0.0f;
            i = 1;
            if (i2 >= this.mNumRows) {
                z = false;
                break;
            }
            ArrayRow arrayRow = this.mRows[i2];
            if (arrayRow.variable.mType != SolverVariable.Type.UNRESTRICTED && arrayRow.constantValue < 0.0f) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            boolean z2 = false;
            int i3 = 0;
            while (!z2) {
                i3 += i;
                float f2 = Float.MAX_VALUE;
                int i4 = -1;
                int i5 = -1;
                int i6 = 0;
                int i7 = 0;
                while (i6 < this.mNumRows) {
                    ArrayRow arrayRow2 = this.mRows[i6];
                    if (arrayRow2.variable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow2.isSimpleDefinition && arrayRow2.constantValue < f) {
                        int currentSize = arrayRow2.variables.getCurrentSize();
                        int i8 = 0;
                        while (i8 < currentSize) {
                            SolverVariable variable = arrayRow2.variables.getVariable(i8);
                            float f3 = arrayRow2.variables.get(variable);
                            if (f3 > f) {
                                for (int i9 = 0; i9 < 9; i9++) {
                                    float f4 = variable.strengthVector[i9] / f3;
                                    if ((f4 < f2 && i9 == i7) || i9 > i7) {
                                        i5 = variable.f2id;
                                        i7 = i9;
                                        f2 = f4;
                                        i4 = i6;
                                    }
                                }
                            }
                            i8++;
                            f = 0.0f;
                        }
                    }
                    i6++;
                    f = 0.0f;
                }
                if (i4 != -1) {
                    ArrayRow arrayRow3 = this.mRows[i4];
                    arrayRow3.variable.definitionId = -1;
                    arrayRow3.pivot(this.mCache.mIndexedVariables[i5]);
                    SolverVariable solverVariable = arrayRow3.variable;
                    solverVariable.definitionId = i4;
                    solverVariable.updateReferencesWithNewDefinition(this, arrayRow3);
                } else {
                    z2 = true;
                }
                if (i3 > this.mNumColumns / 2) {
                    z2 = true;
                }
                f = 0.0f;
                i = 1;
            }
        }
        optimize(priorityGoalRow);
        computeValues();
    }

    public final void optimize(ArrayRow arrayRow) {
        for (int i = 0; i < this.mNumColumns; i++) {
            this.mAlreadyTestedCandidates[i] = false;
        }
        boolean z = false;
        int i2 = 0;
        while (!z) {
            i2++;
            if (i2 >= this.mNumColumns * 2) {
                return;
            }
            SolverVariable solverVariable = arrayRow.variable;
            if (solverVariable != null) {
                this.mAlreadyTestedCandidates[solverVariable.f2id] = true;
            }
            SolverVariable pivotCandidate = arrayRow.getPivotCandidate(this.mAlreadyTestedCandidates);
            if (pivotCandidate != null) {
                boolean[] zArr = this.mAlreadyTestedCandidates;
                int i3 = pivotCandidate.f2id;
                if (zArr[i3]) {
                    return;
                } else {
                    zArr[i3] = true;
                }
            }
            if (pivotCandidate != null) {
                float f = Float.MAX_VALUE;
                int i4 = -1;
                for (int i5 = 0; i5 < this.mNumRows; i5++) {
                    ArrayRow arrayRow2 = this.mRows[i5];
                    if (arrayRow2.variable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow2.isSimpleDefinition && arrayRow2.variables.contains(pivotCandidate)) {
                        float f2 = arrayRow2.variables.get(pivotCandidate);
                        if (f2 < 0.0f) {
                            float f3 = (-arrayRow2.constantValue) / f2;
                            if (f3 < f) {
                                i4 = i5;
                                f = f3;
                            }
                        }
                    }
                }
                if (i4 > -1) {
                    ArrayRow arrayRow3 = this.mRows[i4];
                    arrayRow3.variable.definitionId = -1;
                    arrayRow3.pivot(pivotCandidate);
                    SolverVariable solverVariable2 = arrayRow3.variable;
                    solverVariable2.definitionId = i4;
                    solverVariable2.updateReferencesWithNewDefinition(this, arrayRow3);
                }
            } else {
                z = true;
            }
        }
    }

    public final void reset() {
        Cache cache;
        int i = 0;
        while (true) {
            cache = this.mCache;
            SolverVariable[] solverVariableArr = cache.mIndexedVariables;
            if (i >= solverVariableArr.length) {
                break;
            }
            SolverVariable solverVariable = solverVariableArr[i];
            if (solverVariable != null) {
                solverVariable.reset();
            }
            i++;
        }
        SolverVariable[] solverVariableArr2 = this.mPoolVariables;
        int i2 = this.mPoolVariablesCount;
        Pools$SimplePool pools$SimplePool = cache.solverVariablePool;
        pools$SimplePool.getClass();
        if (i2 > solverVariableArr2.length) {
            i2 = solverVariableArr2.length;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable solverVariable2 = solverVariableArr2[i3];
            int i4 = pools$SimplePool.mPoolSize;
            Object[] objArr = pools$SimplePool.mPool;
            if (i4 < objArr.length) {
                objArr[i4] = solverVariable2;
                pools$SimplePool.mPoolSize = i4 + 1;
            }
        }
        this.mPoolVariablesCount = 0;
        Arrays.fill(cache.mIndexedVariables, (Object) null);
        this.mVariablesID = 0;
        PriorityGoalRow priorityGoalRow = this.mGoal;
        priorityGoalRow.numGoals = 0;
        priorityGoalRow.constantValue = 0.0f;
        this.mNumColumns = 1;
        for (int i5 = 0; i5 < this.mNumRows; i5++) {
            ArrayRow arrayRow = this.mRows[i5];
        }
        for (int i6 = 0; i6 < this.mNumRows; i6++) {
            ArrayRow[] arrayRowArr = this.mRows;
            ArrayRow arrayRow2 = arrayRowArr[i6];
            if (arrayRow2 != null) {
                Pools$SimplePool pools$SimplePool2 = cache.arrayRowPool;
                int i7 = pools$SimplePool2.mPoolSize;
                Object[] objArr2 = pools$SimplePool2.mPool;
                if (i7 < objArr2.length) {
                    objArr2[i7] = arrayRow2;
                    pools$SimplePool2.mPoolSize = i7 + 1;
                }
            }
            arrayRowArr[i6] = null;
        }
        this.mNumRows = 0;
        this.mTempGoal = new ArrayRow(cache);
    }

    public final void addEquality(SolverVariable solverVariable, int i) {
        int i2 = solverVariable.definitionId;
        if (i2 == -1) {
            solverVariable.setFinalValue(this, i);
            for (int i3 = 0; i3 < this.mVariablesID + 1; i3++) {
                SolverVariable solverVariable2 = this.mCache.mIndexedVariables[i3];
            }
            return;
        }
        if (i2 != -1) {
            ArrayRow arrayRow = this.mRows[i2];
            if (arrayRow.isSimpleDefinition) {
                arrayRow.constantValue = i;
                return;
            }
            if (arrayRow.variables.getCurrentSize() == 0) {
                arrayRow.isSimpleDefinition = true;
                arrayRow.constantValue = i;
                return;
            }
            ArrayRow createRow = createRow();
            if (i < 0) {
                createRow.constantValue = i * (-1);
                createRow.variables.put(solverVariable, 1.0f);
            } else {
                createRow.constantValue = i;
                createRow.variables.put(solverVariable, -1.0f);
            }
            addConstraint(createRow);
            return;
        }
        ArrayRow createRow2 = createRow();
        createRow2.variable = solverVariable;
        float f = i;
        solverVariable.computedValue = f;
        createRow2.constantValue = f;
        createRow2.isSimpleDefinition = true;
        addConstraint(createRow2);
    }
}
