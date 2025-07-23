package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PropertyReference2 extends PropertyReference implements KProperty2 {
    public PropertyReference2() {
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KCallable computeReflected() {
        Reflection.factory.getClass();
        return this;
    }

    public final void getGetter$2() {
        ((PropertyReference2) ((KProperty2) getReflected())).getGetter$2();
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((PropertyReference2Impl) this).getGetter$2();
        throw null;
    }

    public PropertyReference2(Class cls, String str, String str2, int i) {
        super(CallableReference.NO_RECEIVER, cls, str, str2, i);
    }
}
