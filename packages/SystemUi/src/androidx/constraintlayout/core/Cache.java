package androidx.constraintlayout.core;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Cache {
    public final Pools$SimplePool arrayRowPool;
    public SolverVariable[] mIndexedVariables;
    public final Pools$SimplePool solverVariablePool;

    public Cache() {
        new Pools$SimplePool(256);
        this.arrayRowPool = new Pools$SimplePool(256);
        this.solverVariablePool = new Pools$SimplePool(256);
        this.mIndexedVariables = new SolverVariable[32];
    }
}
