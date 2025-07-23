package androidx.constraintlayout.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
