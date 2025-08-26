package androidx.constraintlayout.core;

/* loaded from: classes.dex */
public class Cache {
    public final Pools$SimplePool mArrayRowPool;
    public SolverVariable[] mIndexedVariables;
    public final Pools$SimplePool mSolverVariablePool;

    public Cache() {
        new Pools$SimplePool(256);
        this.mArrayRowPool = new Pools$SimplePool(256);
        this.mSolverVariablePool = new Pools$SimplePool(256);
        this.mIndexedVariables = new SolverVariable[32];
    }
}
