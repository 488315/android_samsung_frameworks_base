package kotlin.jvm.internal;

import java.io.Serializable;

/* loaded from: classes4.dex */
public abstract class Lambda<R> implements FunctionBase, Serializable {
    private final int arity;

    public Lambda(int i) {
        this.arity = i;
    }

    @Override // kotlin.jvm.internal.FunctionBase
    public int getArity() {
        return this.arity;
    }

    public String toString() {
        Reflection.factory.getClass();
        return ReflectionFactory.renderLambdaToString(this);
    }
}
