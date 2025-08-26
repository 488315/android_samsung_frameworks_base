package androidx.constraintlayout.core;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import java.util.Arrays;

/* loaded from: classes.dex */
public class LinearSystem {
    public static boolean USE_DEPENDENCY_ORDERING = false;
    public static Metrics sMetrics;
    public final Cache mCache;
    public final PriorityGoalRow mGoal;
    public ArrayRow mTempGoal;
    public int mPoolSize = 1000;
    public boolean hasSimpleDefinition = false;
    public int mVariablesID = 0;
    public int mTableSize = 32;
    public int mMaxColumns = 32;
    public boolean newgraphOptimizer = false;
    public boolean[] mAlreadyTestedCandidates = new boolean[32];
    public int mNumColumns = 1;
    public int mNumRows = 0;
    public int mMaxRows = 32;
    public SolverVariable[] mPoolVariables = new SolverVariable[1000];
    public int mPoolVariablesCount = 0;
    public ArrayRow[] mRows = new ArrayRow[32];

    public LinearSystem() {
        releaseRows();
        Cache cache = new Cache();
        this.mCache = cache;
        this.mGoal = new PriorityGoalRow(cache);
        this.mTempGoal = new ArrayRow(cache);
    }

    public static int getObjectVariableValue(Object obj) {
        SolverVariable solverVariable = ((ConstraintAnchor) obj).mSolverVariable;
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v0 */
    public final SolverVariable acquireSolverVariable(SolverVariable.Type type, String str) {
        Pools$SimplePool pools$SimplePool = this.mCache.mSolverVariablePool;
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
        int i4 = this.mPoolSize;
        if (i3 >= i4) {
            int i5 = i4 * 2;
            this.mPoolSize = i5;
            this.mPoolVariables = (SolverVariable[]) Arrays.copyOf(this.mPoolVariables, i5);
        }
        SolverVariable[] solverVariableArr = this.mPoolVariables;
        int i6 = this.mPoolVariablesCount;
        this.mPoolVariablesCount = i6 + 1;
        solverVariableArr[i6] = solverVariable2;
        return solverVariable2;
    }

    public final void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2, int i3) {
        ArrayRow arrayRowCreateRow = createRow();
        if (solverVariable2 == solverVariable3) {
            arrayRowCreateRow.variables.put(solverVariable, 1.0f);
            arrayRowCreateRow.variables.put(solverVariable4, 1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, -2.0f);
        } else if (f == 0.5f) {
            arrayRowCreateRow.variables.put(solverVariable, 1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, -1.0f);
            arrayRowCreateRow.variables.put(solverVariable3, -1.0f);
            arrayRowCreateRow.variables.put(solverVariable4, 1.0f);
            if (i > 0 || i2 > 0) {
                arrayRowCreateRow.mConstantValue = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            arrayRowCreateRow.variables.put(solverVariable, -1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, 1.0f);
            arrayRowCreateRow.mConstantValue = i;
        } else if (f >= 1.0f) {
            arrayRowCreateRow.variables.put(solverVariable4, -1.0f);
            arrayRowCreateRow.variables.put(solverVariable3, 1.0f);
            arrayRowCreateRow.mConstantValue = -i2;
        } else {
            float f2 = 1.0f - f;
            arrayRowCreateRow.variables.put(solverVariable, f2 * 1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, f2 * (-1.0f));
            arrayRowCreateRow.variables.put(solverVariable3, (-1.0f) * f);
            arrayRowCreateRow.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                arrayRowCreateRow.mConstantValue = (i2 * f) + ((-i) * f2);
            }
        }
        if (i3 != 8) {
            arrayRowCreateRow.addError(this, i3);
        }
        addConstraint(arrayRowCreateRow);
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:155:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addConstraint(ArrayRow arrayRow) {
        boolean z;
        boolean z2;
        SolverVariable solverVariable;
        SolverVariable solverVariablePickPivotInVariables;
        boolean z3 = true;
        if (this.mNumRows + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (arrayRow.mIsSimpleDefinition) {
            z = false;
        } else {
            if (this.mRows.length != 0) {
                boolean z4 = false;
                while (!z4) {
                    int currentSize = arrayRow.variables.getCurrentSize();
                    for (int i = 0; i < currentSize; i++) {
                        SolverVariable variable = arrayRow.variables.getVariable(i);
                        if (variable.mDefinitionId != -1 || variable.isFinalValue) {
                            arrayRow.mVariablesToUpdate.add(variable);
                        }
                    }
                    int size = arrayRow.mVariablesToUpdate.size();
                    if (size > 0) {
                        for (int i2 = 0; i2 < size; i2++) {
                            SolverVariable solverVariable2 = (SolverVariable) arrayRow.mVariablesToUpdate.get(i2);
                            if (solverVariable2.isFinalValue) {
                                arrayRow.updateFromFinalVariable(this, solverVariable2, true);
                            } else {
                                arrayRow.updateFromRow(this, this.mRows[solverVariable2.mDefinitionId], true);
                            }
                        }
                        arrayRow.mVariablesToUpdate.clear();
                    } else {
                        z4 = true;
                    }
                }
                if (arrayRow.mVariable != null && arrayRow.variables.getCurrentSize() == 0) {
                    arrayRow.mIsSimpleDefinition = true;
                    this.hasSimpleDefinition = true;
                }
            }
            if (arrayRow.isEmpty()) {
                return;
            }
            float f = arrayRow.mConstantValue;
            float f2 = 0.0f;
            if (f < 0.0f) {
                arrayRow.mConstantValue = f * (-1.0f);
                ArrayLinkedVariables arrayLinkedVariables = arrayRow.variables;
                int i3 = arrayLinkedVariables.mHead;
                for (int i4 = 0; i3 != -1 && i4 < arrayLinkedVariables.mCurrentSize; i4++) {
                    float[] fArr = arrayLinkedVariables.mArrayValues;
                    fArr[i3] = fArr[i3] * (-1.0f);
                    i3 = arrayLinkedVariables.mArrayNextIndices[i3];
                }
            }
            int currentSize2 = arrayRow.variables.getCurrentSize();
            float f3 = 0.0f;
            float f4 = 0.0f;
            SolverVariable solverVariable3 = null;
            SolverVariable solverVariable4 = null;
            int i5 = 0;
            boolean z5 = false;
            boolean z6 = false;
            while (i5 < currentSize2) {
                float variableValue = arrayRow.variables.getVariableValue(i5);
                SolverVariable variable2 = arrayRow.variables.getVariable(i5);
                float f5 = f2;
                if (variable2.mType == SolverVariable.Type.UNRESTRICTED) {
                    if (solverVariable3 == null) {
                        z5 = variable2.usageInRowCount <= 1;
                    } else if (f3 > variableValue) {
                        if (variable2.usageInRowCount <= 1) {
                        }
                    } else if (z5 || variable2.usageInRowCount > 1) {
                    }
                    solverVariable3 = variable2;
                    f3 = variableValue;
                } else if (solverVariable3 == null && variableValue < f5) {
                    if (solverVariable4 == null) {
                        z6 = variable2.usageInRowCount <= 1;
                    } else if (f4 > variableValue) {
                        if (variable2.usageInRowCount <= 1) {
                        }
                    } else if (z6 || variable2.usageInRowCount > 1) {
                    }
                    solverVariable4 = variable2;
                    f4 = variableValue;
                }
                i5++;
                f2 = f5;
            }
            float f6 = f2;
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
                arrayRow.mIsSimpleDefinition = true;
            }
            if (z2) {
                if (this.mNumColumns + 1 >= this.mMaxColumns) {
                    increaseTableSize();
                }
                SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
                int i6 = this.mVariablesID + 1;
                this.mVariablesID = i6;
                this.mNumColumns++;
                solverVariableAcquireSolverVariable.id = i6;
                Cache cache = this.mCache;
                cache.mIndexedVariables[i6] = solverVariableAcquireSolverVariable;
                arrayRow.mVariable = solverVariableAcquireSolverVariable;
                int i7 = this.mNumRows;
                addRow(arrayRow);
                if (this.mNumRows == i7 + 1) {
                    ArrayRow arrayRow2 = this.mTempGoal;
                    arrayRow2.mVariable = null;
                    arrayRow2.variables.clear();
                    for (int i8 = 0; i8 < arrayRow.variables.getCurrentSize(); i8++) {
                        arrayRow2.variables.add(arrayRow.variables.getVariable(i8), arrayRow.variables.getVariableValue(i8), true);
                    }
                    optimize(this.mTempGoal);
                    if (solverVariableAcquireSolverVariable.mDefinitionId == -1) {
                        if (arrayRow.mVariable == solverVariableAcquireSolverVariable && (solverVariablePickPivotInVariables = arrayRow.pickPivotInVariables(null, solverVariableAcquireSolverVariable)) != null) {
                            arrayRow.pivot(solverVariablePickPivotInVariables);
                        }
                        if (!arrayRow.mIsSimpleDefinition) {
                            arrayRow.mVariable.updateReferencesWithNewDefinition(this, arrayRow);
                        }
                        cache.mArrayRowPool.release(arrayRow);
                        this.mNumRows--;
                    }
                }
                solverVariable = arrayRow.mVariable;
                if (solverVariable != null) {
                }
            } else {
                z3 = false;
                solverVariable = arrayRow.mVariable;
                if (solverVariable != null) {
                    return;
                }
                if (solverVariable.mType != SolverVariable.Type.UNRESTRICTED && arrayRow.mConstantValue < f6) {
                    return;
                } else {
                    z = z3;
                }
            }
        }
        if (z) {
            return;
        }
        addRow(arrayRow);
    }

    public final void addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.mSimpleEquations++;
        }
        if (i2 == 8 && solverVariable2.isFinalValue && solverVariable.mDefinitionId == -1) {
            solverVariable.setFinalValue(this, solverVariable2.computedValue + i);
            return;
        }
        ArrayRow arrayRowCreateRow = createRow();
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            arrayRowCreateRow.mConstantValue = i;
        }
        if (z) {
            arrayRowCreateRow.variables.put(solverVariable, 1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, -1.0f);
        } else {
            arrayRowCreateRow.variables.put(solverVariable, -1.0f);
            arrayRowCreateRow.variables.put(solverVariable2, 1.0f);
        }
        if (i2 != 8) {
            arrayRowCreateRow.addError(this, i2);
        }
        addConstraint(arrayRowCreateRow);
    }

    public final void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i);
        if (i2 != 8) {
            arrayRowCreateRow.variables.put(createErrorVariable(i2, null), (int) (arrayRowCreateRow.variables.get(solverVariableCreateSlackVariable) * (-1.0f)));
        }
        addConstraint(arrayRowCreateRow);
    }

    public final void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowLowerThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i);
        if (i2 != 8) {
            arrayRowCreateRow.variables.put(createErrorVariable(i2, null), (int) (arrayRowCreateRow.variables.get(solverVariableCreateSlackVariable) * (-1.0f)));
        }
        addConstraint(arrayRowCreateRow);
    }

    public final void addRow(ArrayRow arrayRow) {
        int i;
        if (arrayRow.mIsSimpleDefinition) {
            arrayRow.mVariable.setFinalValue(this, arrayRow.mConstantValue);
        } else {
            ArrayRow[] arrayRowArr = this.mRows;
            int i2 = this.mNumRows;
            arrayRowArr[i2] = arrayRow;
            SolverVariable solverVariable = arrayRow.mVariable;
            solverVariable.mDefinitionId = i2;
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
                if (arrayRow2 != null && arrayRow2.mIsSimpleDefinition) {
                    arrayRow2.mVariable.setFinalValue(this, arrayRow2.mConstantValue);
                    this.mCache.mArrayRowPool.release(arrayRow2);
                    this.mRows[i3] = null;
                    int i4 = i3 + 1;
                    int i5 = i4;
                    while (true) {
                        i = this.mNumRows;
                        if (i4 >= i) {
                            break;
                        }
                        ArrayRow[] arrayRowArr2 = this.mRows;
                        int i6 = i4 - 1;
                        ArrayRow arrayRow3 = arrayRowArr2[i4];
                        arrayRowArr2[i6] = arrayRow3;
                        SolverVariable solverVariable2 = arrayRow3.mVariable;
                        if (solverVariable2.mDefinitionId == i4) {
                            solverVariable2.mDefinitionId = i6;
                        }
                        i5 = i4;
                        i4++;
                    }
                    if (i5 < i) {
                        this.mRows[i5] = null;
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
            arrayRow.mVariable.computedValue = arrayRow.mConstantValue;
        }
    }

    public final SolverVariable createErrorVariable(int i, String str) {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR, str);
        int i2 = this.mVariablesID + 1;
        this.mVariablesID = i2;
        this.mNumColumns++;
        solverVariableAcquireSolverVariable.id = i2;
        solverVariableAcquireSolverVariable.strength = i;
        this.mCache.mIndexedVariables[i2] = solverVariableAcquireSolverVariable;
        PriorityGoalRow priorityGoalRow = this.mGoal;
        priorityGoalRow.mAccessor.mVariable = solverVariableAcquireSolverVariable;
        Arrays.fill(solverVariableAcquireSolverVariable.mGoalStrengthVector, 0.0f);
        solverVariableAcquireSolverVariable.mGoalStrengthVector[solverVariableAcquireSolverVariable.strength] = 1.0f;
        priorityGoalRow.addToGoal(solverVariableAcquireSolverVariable);
        return solverVariableAcquireSolverVariable;
    }

    public final SolverVariable createObjectVariable(Object obj) {
        if (obj == null) {
            return null;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (!(obj instanceof ConstraintAnchor)) {
            return null;
        }
        ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
        SolverVariable solverVariable = constraintAnchor.mSolverVariable;
        if (solverVariable == null) {
            constraintAnchor.resetSolverVariable();
            solverVariable = constraintAnchor.mSolverVariable;
        }
        int i = solverVariable.id;
        Cache cache = this.mCache;
        if (i != -1 && i <= this.mVariablesID && cache.mIndexedVariables[i] != null) {
            return solverVariable;
        }
        if (i != -1) {
            solverVariable.reset();
        }
        int i2 = this.mVariablesID + 1;
        this.mVariablesID = i2;
        this.mNumColumns++;
        solverVariable.id = i2;
        solverVariable.mType = SolverVariable.Type.UNRESTRICTED;
        cache.mIndexedVariables[i2] = solverVariable;
        return solverVariable;
    }

    public final ArrayRow createRow() {
        Object obj;
        Cache cache = this.mCache;
        Pools$SimplePool pools$SimplePool = cache.mArrayRowPool;
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
        arrayRow.mVariable = null;
        arrayRow.variables.clear();
        arrayRow.mConstantValue = 0.0f;
        arrayRow.mIsSimpleDefinition = false;
        return arrayRow;
    }

    public final SolverVariable createSlackVariable() {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
        int i = this.mVariablesID + 1;
        this.mVariablesID = i;
        this.mNumColumns++;
        solverVariableAcquireSolverVariable.id = i;
        this.mCache.mIndexedVariables[i] = solverVariableAcquireSolverVariable;
        return solverVariableAcquireSolverVariable;
    }

    public final void increaseTableSize() {
        int i = this.mTableSize * 2;
        this.mTableSize = i;
        this.mRows = (ArrayRow[]) Arrays.copyOf(this.mRows, i);
        Cache cache = this.mCache;
        cache.mIndexedVariables = (SolverVariable[]) Arrays.copyOf(cache.mIndexedVariables, this.mTableSize);
        int i2 = this.mTableSize;
        this.mAlreadyTestedCandidates = new boolean[i2];
        this.mMaxColumns = i2;
        this.mMaxRows = i2;
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.maxTableSize = Math.max(metrics.maxTableSize, i2);
            long j = sMetrics.maxTableSize;
        }
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
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.graphOptimizer++;
        }
        for (int i = 0; i < this.mNumRows; i++) {
            if (!this.mRows[i].mIsSimpleDefinition) {
                minimizeGoal(priorityGoalRow);
                return;
            }
        }
        computeValues();
    }

    public final void minimizeGoal(PriorityGoalRow priorityGoalRow) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.maxVariables = Math.max(metrics.maxVariables, this.mNumColumns);
            Metrics metrics2 = sMetrics;
            metrics2.maxRows = Math.max(metrics2.maxRows, this.mNumRows);
        }
        int i = 0;
        while (true) {
            if (i >= this.mNumRows) {
                break;
            }
            ArrayRow arrayRow = this.mRows[i];
            if (arrayRow.mVariable.mType != SolverVariable.Type.UNRESTRICTED) {
                float f = 0.0f;
                if (arrayRow.mConstantValue < 0.0f) {
                    boolean z = false;
                    int i2 = 0;
                    while (!z) {
                        i2++;
                        float f2 = Float.MAX_VALUE;
                        int i3 = -1;
                        int i4 = -1;
                        int i5 = 0;
                        int i6 = 0;
                        while (i5 < this.mNumRows) {
                            ArrayRow arrayRow2 = this.mRows[i5];
                            if (arrayRow2.mVariable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow2.mIsSimpleDefinition && arrayRow2.mConstantValue < f) {
                                int currentSize = arrayRow2.variables.getCurrentSize();
                                int i7 = 0;
                                while (i7 < currentSize) {
                                    SolverVariable variable = arrayRow2.variables.getVariable(i7);
                                    float f3 = arrayRow2.variables.get(variable);
                                    if (f3 > f) {
                                        for (int i8 = 0; i8 < 9; i8++) {
                                            float f4 = variable.mStrengthVector[i8] / f3;
                                            if ((f4 < f2 && i8 == i6) || i8 > i6) {
                                                i6 = i8;
                                                i4 = variable.id;
                                                i3 = i5;
                                                f2 = f4;
                                            }
                                        }
                                    }
                                    i7++;
                                    f = 0.0f;
                                }
                            }
                            i5++;
                            f = 0.0f;
                        }
                        if (i3 != -1) {
                            ArrayRow arrayRow3 = this.mRows[i3];
                            arrayRow3.mVariable.mDefinitionId = -1;
                            arrayRow3.pivot(this.mCache.mIndexedVariables[i4]);
                            SolverVariable solverVariable = arrayRow3.mVariable;
                            solverVariable.mDefinitionId = i3;
                            solverVariable.updateReferencesWithNewDefinition(this, arrayRow3);
                        } else {
                            z = true;
                        }
                        if (i2 > this.mNumColumns / 2) {
                            z = true;
                        }
                        f = 0.0f;
                    }
                }
            }
            i++;
        }
        optimize(priorityGoalRow);
        computeValues();
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0094 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void optimize(ArrayRow arrayRow) {
        boolean z;
        int i = 0;
        for (int i2 = 0; i2 < this.mNumColumns; i2++) {
            this.mAlreadyTestedCandidates[i2] = false;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            i3++;
            if (i3 >= this.mNumColumns * 2) {
                return;
            }
            SolverVariable solverVariable = arrayRow.mVariable;
            if (solverVariable != null) {
                this.mAlreadyTestedCandidates[solverVariable.id] = true;
            }
            SolverVariable pivotCandidate = arrayRow.getPivotCandidate(this.mAlreadyTestedCandidates);
            if (pivotCandidate != null) {
                boolean[] zArr = this.mAlreadyTestedCandidates;
                int i4 = pivotCandidate.id;
                if (zArr[i4]) {
                    return;
                } else {
                    zArr[i4] = true;
                }
            }
            if (pivotCandidate != null) {
                float f = Float.MAX_VALUE;
                int i5 = -1;
                for (int i6 = i; i6 < this.mNumRows; i6++) {
                    ArrayRow arrayRow2 = this.mRows[i6];
                    if (arrayRow2.mVariable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow2.mIsSimpleDefinition) {
                        ArrayLinkedVariables arrayLinkedVariables = arrayRow2.variables;
                        int i7 = arrayLinkedVariables.mHead;
                        if (i7 == -1) {
                            z = false;
                            if (!z) {
                                float f2 = arrayRow2.variables.get(pivotCandidate);
                                if (f2 < 0.0f) {
                                    float f3 = (-arrayRow2.mConstantValue) / f2;
                                    if (f3 < f) {
                                        i5 = i6;
                                        f = f3;
                                    }
                                }
                            }
                        } else {
                            for (int i8 = 0; i7 != -1 && i8 < arrayLinkedVariables.mCurrentSize; i8++) {
                                if (arrayLinkedVariables.mArrayIndices[i7] == pivotCandidate.id) {
                                    z = true;
                                    break;
                                }
                                i7 = arrayLinkedVariables.mArrayNextIndices[i7];
                            }
                            z = false;
                            if (!z) {
                            }
                        }
                    }
                }
                if (i5 > -1) {
                    ArrayRow arrayRow3 = this.mRows[i5];
                    arrayRow3.mVariable.mDefinitionId = -1;
                    arrayRow3.pivot(pivotCandidate);
                    SolverVariable solverVariable2 = arrayRow3.mVariable;
                    solverVariable2.mDefinitionId = i5;
                    solverVariable2.updateReferencesWithNewDefinition(this, arrayRow3);
                }
            } else {
                z2 = true;
            }
            i = 0;
        }
    }

    public final void releaseRows() {
        for (int i = 0; i < this.mNumRows; i++) {
            ArrayRow arrayRow = this.mRows[i];
            if (arrayRow != null) {
                this.mCache.mArrayRowPool.release(arrayRow);
            }
            this.mRows[i] = null;
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
        Pools$SimplePool pools$SimplePool = cache.mSolverVariablePool;
        SolverVariable[] solverVariableArr2 = this.mPoolVariables;
        int length = this.mPoolVariablesCount;
        pools$SimplePool.getClass();
        if (length > solverVariableArr2.length) {
            length = solverVariableArr2.length;
        }
        for (int i2 = 0; i2 < length; i2++) {
            SolverVariable solverVariable2 = solverVariableArr2[i2];
            int i3 = pools$SimplePool.mPoolSize;
            Object[] objArr = pools$SimplePool.mPool;
            if (i3 < objArr.length) {
                objArr[i3] = solverVariable2;
                pools$SimplePool.mPoolSize = i3 + 1;
            }
        }
        this.mPoolVariablesCount = 0;
        Arrays.fill(cache.mIndexedVariables, (Object) null);
        this.mVariablesID = 0;
        PriorityGoalRow priorityGoalRow = this.mGoal;
        priorityGoalRow.mNumGoals = 0;
        priorityGoalRow.mConstantValue = 0.0f;
        this.mNumColumns = 1;
        for (int i4 = 0; i4 < this.mNumRows; i4++) {
            ArrayRow arrayRow = this.mRows[i4];
        }
        releaseRows();
        this.mNumRows = 0;
        this.mTempGoal = new ArrayRow(cache);
    }

    public final void addEquality(SolverVariable solverVariable, int i) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.mSimpleEquations++;
        }
        int i2 = solverVariable.mDefinitionId;
        if (i2 == -1) {
            solverVariable.setFinalValue(this, i);
            for (int i3 = 0; i3 < this.mVariablesID + 1; i3++) {
                SolverVariable solverVariable2 = this.mCache.mIndexedVariables[i3];
            }
            return;
        }
        if (i2 != -1) {
            ArrayRow arrayRow = this.mRows[i2];
            if (arrayRow.mIsSimpleDefinition) {
                arrayRow.mConstantValue = i;
                return;
            }
            if (arrayRow.variables.getCurrentSize() == 0) {
                arrayRow.mIsSimpleDefinition = true;
                arrayRow.mConstantValue = i;
                return;
            }
            ArrayRow arrayRowCreateRow = createRow();
            if (i < 0) {
                arrayRowCreateRow.mConstantValue = i * (-1);
                arrayRowCreateRow.variables.put(solverVariable, 1.0f);
            } else {
                arrayRowCreateRow.mConstantValue = i;
                arrayRowCreateRow.variables.put(solverVariable, -1.0f);
            }
            addConstraint(arrayRowCreateRow);
            return;
        }
        ArrayRow arrayRowCreateRow2 = createRow();
        arrayRowCreateRow2.mVariable = solverVariable;
        float f = i;
        solverVariable.computedValue = f;
        arrayRowCreateRow2.mConstantValue = f;
        arrayRowCreateRow2.mIsSimpleDefinition = true;
        addConstraint(arrayRowCreateRow2);
    }
}
