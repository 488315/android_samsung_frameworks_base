package androidx.constraintlayout.core;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class LinearSystem {
    public static boolean USE_DEPENDENCY_ORDERING = false;
    private static int sPoolSize = 1000;
    final Cache mCache;
    private PriorityGoalRow mGoal;
    ArrayRow[] mRows;
    private ArrayRow mTempGoal;
    public boolean hasSimpleDefinition = false;
    int mVariablesID = 0;
    private int mTableSize = 32;
    private int mMaxColumns = 32;
    public boolean newgraphOptimizer = false;
    private boolean[] mAlreadyTestedCandidates = new boolean[32];
    int mNumColumns = 1;
    int mNumRows = 0;
    private int mMaxRows = 32;
    private SolverVariable[] mPoolVariables = new SolverVariable[sPoolSize];
    private int mPoolVariablesCount = 0;

    interface Row {
        SolverVariable getPivotCandidate(boolean[] zArr);
    }

    public LinearSystem() {
        this.mRows = null;
        this.mRows = new ArrayRow[32];
        for (int i = 0; i < this.mNumRows; i++) {
            ArrayRow arrayRow = this.mRows[i];
            if (arrayRow != null) {
                this.mCache.mArrayRowPool.release(arrayRow);
            }
            this.mRows[i] = null;
        }
        Cache cache = new Cache();
        this.mCache = cache;
        this.mGoal = new PriorityGoalRow(cache);
        this.mTempGoal = new ArrayRow(cache);
    }

    private SolverVariable acquireSolverVariable(SolverVariable.Type type) {
        SolverVariable solverVariable = (SolverVariable) this.mCache.mSolverVariablePool.acquire();
        if (solverVariable == null) {
            solverVariable = new SolverVariable(type);
            solverVariable.mType = type;
        } else {
            solverVariable.reset();
            solverVariable.mType = type;
        }
        int i = this.mPoolVariablesCount;
        int i2 = sPoolSize;
        if (i >= i2) {
            int i3 = i2 * 2;
            sPoolSize = i3;
            this.mPoolVariables = (SolverVariable[]) Arrays.copyOf(this.mPoolVariables, i3);
        }
        SolverVariable[] solverVariableArr = this.mPoolVariables;
        int i4 = this.mPoolVariablesCount;
        this.mPoolVariablesCount = i4 + 1;
        solverVariableArr[i4] = solverVariable;
        return solverVariable;
    }

    private void addRow(ArrayRow arrayRow) {
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

    private void computeValues() {
        for (int i = 0; i < this.mNumRows; i++) {
            ArrayRow arrayRow = this.mRows[i];
            arrayRow.mVariable.computedValue = arrayRow.mConstantValue;
        }
    }

    public static int getObjectVariableValue(ConstraintAnchor constraintAnchor) {
        SolverVariable solverVariable = constraintAnchor.getSolverVariable();
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    private void increaseTableSize() {
        int i = this.mTableSize * 2;
        this.mTableSize = i;
        this.mRows = (ArrayRow[]) Arrays.copyOf(this.mRows, i);
        Cache cache = this.mCache;
        cache.mIndexedVariables = (SolverVariable[]) Arrays.copyOf(cache.mIndexedVariables, this.mTableSize);
        int i2 = this.mTableSize;
        this.mAlreadyTestedCandidates = new boolean[i2];
        this.mMaxColumns = i2;
        this.mMaxRows = i2;
    }

    private void optimize(ArrayRow arrayRow) {
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
            SolverVariable solverVariable = arrayRow.mVariable;
            if (solverVariable != null) {
                this.mAlreadyTestedCandidates[solverVariable.f1id] = true;
            }
            SolverVariable pivotCandidate = arrayRow.getPivotCandidate(this.mAlreadyTestedCandidates);
            if (pivotCandidate != null) {
                boolean[] zArr = this.mAlreadyTestedCandidates;
                int i3 = pivotCandidate.f1id;
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
                    if (arrayRow2.mVariable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow2.mIsSimpleDefinition && arrayRow2.variables.contains(pivotCandidate)) {
                        float f2 = arrayRow2.variables.get(pivotCandidate);
                        if (f2 < 0.0f) {
                            float f3 = (-arrayRow2.mConstantValue) / f2;
                            if (f3 < f) {
                                i4 = i5;
                                f = f3;
                            }
                        }
                    }
                }
                if (i4 > -1) {
                    ArrayRow arrayRow3 = this.mRows[i4];
                    arrayRow3.mVariable.mDefinitionId = -1;
                    arrayRow3.pivot(pivotCandidate);
                    SolverVariable solverVariable2 = arrayRow3.mVariable;
                    solverVariable2.mDefinitionId = i4;
                    solverVariable2.updateReferencesWithNewDefinition(this, arrayRow3);
                }
            } else {
                z = true;
            }
        }
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
                createRow.mConstantValue = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            createRow.variables.put(solverVariable, -1.0f);
            createRow.variables.put(solverVariable2, 1.0f);
            createRow.mConstantValue = i;
        } else if (f >= 1.0f) {
            createRow.variables.put(solverVariable4, -1.0f);
            createRow.variables.put(solverVariable3, 1.0f);
            createRow.mConstantValue = -i2;
        } else {
            float f2 = 1.0f - f;
            createRow.variables.put(solverVariable, f2 * 1.0f);
            createRow.variables.put(solverVariable2, f2 * (-1.0f));
            createRow.variables.put(solverVariable3, (-1.0f) * f);
            createRow.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                createRow.mConstantValue = (i2 * f) + ((-i) * f2);
            }
        }
        if (i3 != 8) {
            createRow.addError(this, i3);
        }
        addConstraint(createRow);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c0, code lost:
    
        if (r5.usageInRowCount <= 1) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cc, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c9, code lost:
    
        if (r5.usageInRowCount <= 1) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00e7, code lost:
    
        if (r5.usageInRowCount <= 1) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00f3, code lost:
    
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00f0, code lost:
    
        if (r5.usageInRowCount <= 1) goto L87;
     */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01b6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addConstraint(ArrayRow arrayRow) {
        boolean z;
        SolverVariable.Type type;
        boolean z2;
        boolean z3;
        SolverVariable solverVariable;
        SolverVariable pickPivot;
        boolean z4 = true;
        if (this.mNumRows + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (arrayRow.mIsSimpleDefinition) {
            z = false;
        } else {
            if (this.mRows.length != 0) {
                boolean z5 = false;
                while (!z5) {
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
                            SolverVariable solverVariable2 = arrayRow.mVariablesToUpdate.get(i2);
                            if (solverVariable2.isFinalValue) {
                                arrayRow.updateFromFinalVariable(this, solverVariable2, true);
                            } else {
                                arrayRow.updateFromRow(this, this.mRows[solverVariable2.mDefinitionId], true);
                            }
                        }
                        arrayRow.mVariablesToUpdate.clear();
                    } else {
                        z5 = true;
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
            if (f < 0.0f) {
                arrayRow.mConstantValue = f * (-1.0f);
                arrayRow.variables.invert();
            }
            int currentSize2 = arrayRow.variables.getCurrentSize();
            float f2 = 0.0f;
            float f3 = 0.0f;
            int i3 = 0;
            SolverVariable solverVariable3 = null;
            SolverVariable solverVariable4 = null;
            boolean z6 = false;
            boolean z7 = false;
            while (true) {
                type = SolverVariable.Type.UNRESTRICTED;
                if (i3 >= currentSize2) {
                    break;
                }
                float variableValue = arrayRow.variables.getVariableValue(i3);
                SolverVariable variable2 = arrayRow.variables.getVariable(i3);
                if (variable2.mType == type) {
                    if (solverVariable3 != null) {
                        if (f2 <= variableValue) {
                            if (!z6) {
                                if (!(variable2.usageInRowCount <= 1)) {
                                }
                            }
                        }
                        z6 = true;
                    }
                    f2 = variableValue;
                    solverVariable3 = variable2;
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
                    f3 = variableValue;
                    solverVariable4 = variable2;
                }
                i3++;
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
                arrayRow.mIsSimpleDefinition = true;
            }
            if (z2) {
                if (this.mNumColumns + 1 >= this.mMaxColumns) {
                    increaseTableSize();
                }
                SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK);
                int i4 = this.mVariablesID + 1;
                this.mVariablesID = i4;
                this.mNumColumns++;
                acquireSolverVariable.f1id = i4;
                Cache cache = this.mCache;
                cache.mIndexedVariables[i4] = acquireSolverVariable;
                arrayRow.mVariable = acquireSolverVariable;
                int i5 = this.mNumRows;
                addRow(arrayRow);
                if (this.mNumRows == i5 + 1) {
                    ArrayRow arrayRow2 = this.mTempGoal;
                    arrayRow2.getClass();
                    arrayRow2.mVariable = null;
                    arrayRow2.variables.clear();
                    for (int i6 = 0; i6 < arrayRow.variables.getCurrentSize(); i6++) {
                        arrayRow2.variables.add(arrayRow.variables.getVariable(i6), arrayRow.variables.getVariableValue(i6), true);
                    }
                    optimize(this.mTempGoal);
                    if (acquireSolverVariable.mDefinitionId == -1) {
                        if (arrayRow.mVariable == acquireSolverVariable && (pickPivot = arrayRow.pickPivot(acquireSolverVariable)) != null) {
                            arrayRow.pivot(pickPivot);
                        }
                        if (!arrayRow.mIsSimpleDefinition) {
                            arrayRow.mVariable.updateReferencesWithNewDefinition(this, arrayRow);
                        }
                        cache.mArrayRowPool.release(arrayRow);
                        this.mNumRows--;
                    }
                    z3 = true;
                    solverVariable = arrayRow.mVariable;
                    if (solverVariable != null || (solverVariable.mType != type && arrayRow.mConstantValue < 0.0f)) {
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
            solverVariable = arrayRow.mVariable;
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
        if (i2 == 8 && solverVariable2.isFinalValue && solverVariable.mDefinitionId == -1) {
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
            createRow.mConstantValue = i;
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
            createRow.variables.put(createErrorVariable(i2), (int) (createRow.variables.get(createSlackVariable) * (-1.0f)));
        }
        addConstraint(createRow);
    }

    public final void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 8) {
            createRow.variables.put(createErrorVariable(i2), (int) (createRow.variables.get(createSlackVariable) * (-1.0f)));
        }
        addConstraint(createRow);
    }

    public final SolverVariable createErrorVariable(int i) {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR);
        int i2 = this.mVariablesID + 1;
        this.mVariablesID = i2;
        this.mNumColumns++;
        acquireSolverVariable.f1id = i2;
        acquireSolverVariable.strength = i;
        this.mCache.mIndexedVariables[i2] = acquireSolverVariable;
        this.mGoal.addError(acquireSolverVariable);
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
            solverVariable = constraintAnchor.getSolverVariable();
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable();
                solverVariable = constraintAnchor.getSolverVariable();
            }
            int i = solverVariable.f1id;
            Cache cache = this.mCache;
            if (i == -1 || i > this.mVariablesID || cache.mIndexedVariables[i] == null) {
                if (i != -1) {
                    solverVariable.reset();
                }
                int i2 = this.mVariablesID + 1;
                this.mVariablesID = i2;
                this.mNumColumns++;
                solverVariable.f1id = i2;
                solverVariable.mType = SolverVariable.Type.UNRESTRICTED;
                cache.mIndexedVariables[i2] = solverVariable;
            }
        }
        return solverVariable;
    }

    public final ArrayRow createRow() {
        Cache cache = this.mCache;
        ArrayRow arrayRow = (ArrayRow) cache.mArrayRowPool.acquire();
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
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK);
        int i = this.mVariablesID + 1;
        this.mVariablesID = i;
        this.mNumColumns++;
        acquireSolverVariable.f1id = i;
        this.mCache.mIndexedVariables[i] = acquireSolverVariable;
        return acquireSolverVariable;
    }

    public final Cache getCache() {
        return this.mCache;
    }

    public final void minimize() throws Exception {
        if (this.mGoal.isEmpty()) {
            computeValues();
            return;
        }
        if (!this.newgraphOptimizer) {
            minimizeGoal(this.mGoal);
            return;
        }
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.mNumRows) {
                z = true;
                break;
            } else if (!this.mRows[i].mIsSimpleDefinition) {
                break;
            } else {
                i++;
            }
        }
        if (z) {
            computeValues();
        } else {
            minimizeGoal(this.mGoal);
        }
    }

    final void minimizeGoal(PriorityGoalRow priorityGoalRow) throws Exception {
        SolverVariable.Type type;
        float f;
        int i;
        boolean z;
        int i2 = 0;
        while (true) {
            int i3 = this.mNumRows;
            type = SolverVariable.Type.UNRESTRICTED;
            f = 0.0f;
            i = 1;
            if (i2 >= i3) {
                z = false;
                break;
            }
            ArrayRow arrayRow = this.mRows[i2];
            if (arrayRow.mVariable.mType != type && arrayRow.mConstantValue < 0.0f) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            boolean z2 = false;
            int i4 = 0;
            while (!z2) {
                i4 += i;
                float f2 = Float.MAX_VALUE;
                int i5 = 0;
                int i6 = -1;
                int i7 = -1;
                int i8 = 0;
                while (i5 < this.mNumRows) {
                    ArrayRow arrayRow2 = this.mRows[i5];
                    if (arrayRow2.mVariable.mType != type && !arrayRow2.mIsSimpleDefinition && arrayRow2.mConstantValue < f) {
                        int currentSize = arrayRow2.variables.getCurrentSize();
                        int i9 = 0;
                        while (i9 < currentSize) {
                            SolverVariable variable = arrayRow2.variables.getVariable(i9);
                            float f3 = arrayRow2.variables.get(variable);
                            if (f3 > f) {
                                for (int i10 = 0; i10 < 9; i10++) {
                                    float f4 = variable.mStrengthVector[i10] / f3;
                                    if ((f4 < f2 && i10 == i8) || i10 > i8) {
                                        i7 = variable.f1id;
                                        i8 = i10;
                                        f2 = f4;
                                        i6 = i5;
                                    }
                                }
                            }
                            i9++;
                            f = 0.0f;
                        }
                    }
                    i5++;
                    f = 0.0f;
                }
                if (i6 != -1) {
                    ArrayRow arrayRow3 = this.mRows[i6];
                    arrayRow3.mVariable.mDefinitionId = -1;
                    arrayRow3.pivot(this.mCache.mIndexedVariables[i7]);
                    SolverVariable solverVariable = arrayRow3.mVariable;
                    solverVariable.mDefinitionId = i6;
                    solverVariable.updateReferencesWithNewDefinition(this, arrayRow3);
                } else {
                    z2 = true;
                }
                if (i4 > this.mNumColumns / 2) {
                    z2 = true;
                }
                f = 0.0f;
                i = 1;
            }
        }
        optimize(priorityGoalRow);
        computeValues();
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
        cache.mSolverVariablePool.releaseAll(this.mPoolVariablesCount, this.mPoolVariables);
        this.mPoolVariablesCount = 0;
        Arrays.fill(cache.mIndexedVariables, (Object) null);
        this.mVariablesID = 0;
        this.mGoal.clear();
        this.mNumColumns = 1;
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            ArrayRow arrayRow = this.mRows[i2];
        }
        for (int i3 = 0; i3 < this.mNumRows; i3++) {
            ArrayRow arrayRow2 = this.mRows[i3];
            if (arrayRow2 != null) {
                cache.mArrayRowPool.release(arrayRow2);
            }
            this.mRows[i3] = null;
        }
        this.mNumRows = 0;
        this.mTempGoal = new ArrayRow(cache);
    }

    public final void addEquality(SolverVariable solverVariable, int i) {
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
            ArrayRow createRow = createRow();
            if (i < 0) {
                createRow.mConstantValue = i * (-1);
                createRow.variables.put(solverVariable, 1.0f);
            } else {
                createRow.mConstantValue = i;
                createRow.variables.put(solverVariable, -1.0f);
            }
            addConstraint(createRow);
            return;
        }
        ArrayRow createRow2 = createRow();
        createRow2.mVariable = solverVariable;
        float f = i;
        solverVariable.computedValue = f;
        createRow2.mConstantValue = f;
        createRow2.mIsSimpleDefinition = true;
        addConstraint(createRow2);
    }
}
