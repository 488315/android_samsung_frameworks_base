package androidx.constraintlayout.core;

/* loaded from: classes.dex */
public final class Cache {
    Pools$SimplePool mArrayRowPool = new Pools$SimplePool();
    Pools$SimplePool mSolverVariablePool = new Pools$SimplePool();
    SolverVariable[] mIndexedVariables = new SolverVariable[32];
}
