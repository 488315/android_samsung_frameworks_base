package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class RestrictedSuspendLambda extends RestrictedContinuationImpl implements FunctionBase {
    private final int arity;

    public RestrictedSuspendLambda(int i, Continuation continuation) {
        super(continuation);
        this.arity = i;
    }

    @Override // kotlin.jvm.internal.FunctionBase
    public int getArity() {
        return this.arity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public String toString() {
        if (getCompletion() != null) {
            return super.toString();
        }
        Reflection.factory.getClass();
        return ReflectionFactory.renderLambdaToString(this);
    }

    public RestrictedSuspendLambda(int i) {
        this(i, null);
    }
}
