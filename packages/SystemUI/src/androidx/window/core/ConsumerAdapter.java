package androidx.window.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConsumerAdapter {
    public final ClassLoader loader;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConsumerHandler implements InvocationHandler {
        public final KClass clazz;
        public final Function1 consumer;

        public ConsumerHandler(KClass kClass, Function1 function1) {
            this.clazz = kClass;
            this.consumer = function1;
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            if (Intrinsics.areEqual(method.getName(), "accept") && objArr != null && objArr.length == 1) {
                KClass kClass = this.clazz;
                Object obj2 = objArr != null ? objArr[0] : null;
                ClassReference classReference = (ClassReference) kClass;
                if (classReference.isInstance(obj2)) {
                    this.consumer.mo779invoke(obj2);
                    return Unit.INSTANCE;
                }
                throw new ClassCastException("Value cannot be cast to " + classReference.getQualifiedName());
            }
            if (Intrinsics.areEqual(method.getName(), "equals") && method.getReturnType().equals(Boolean.TYPE) && objArr != null && objArr.length == 1) {
                return Boolean.valueOf(obj == (objArr != null ? objArr[0] : null));
            }
            if (Intrinsics.areEqual(method.getName(), "hashCode") && method.getReturnType().equals(Integer.TYPE) && objArr == null) {
                return Integer.valueOf(this.consumer.hashCode());
            }
            if (Intrinsics.areEqual(method.getName(), "toString") && method.getReturnType().equals(String.class) && objArr == null) {
                return this.consumer.toString();
            }
            throw new UnsupportedOperationException("Unexpected method call object:" + obj + ", method: " + method + ", args: " + objArr);
        }
    }

    public ConsumerAdapter(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
