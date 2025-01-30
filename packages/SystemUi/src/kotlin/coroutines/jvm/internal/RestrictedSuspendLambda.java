package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class RestrictedSuspendLambda extends RestrictedContinuationImpl implements FunctionBase {
    private final int arity;

    public RestrictedSuspendLambda(int i, Continuation<Object> continuation) {
        super(continuation);
        this.arity = i;
    }

    @Override // kotlin.jvm.internal.FunctionBase
    public final int getArity() {
        return this.arity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final String toString() {
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
