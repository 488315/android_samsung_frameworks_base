package kotlin.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PlatformImplementations {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReflectThrowable {
        public static final Method addSuppressed;
        public static final Method getSuppressed;

        static {
            Method method;
            Method method2;
            new ReflectThrowable();
            Method[] methods = Throwable.class.getMethods();
            methods.getClass();
            int length = methods.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                method = null;
                if (i2 >= length) {
                    method2 = null;
                    break;
                }
                method2 = methods[i2];
                if (Intrinsics.areEqual(method2.getName(), "addSuppressed")) {
                    Class<?>[] parameterTypes = method2.getParameterTypes();
                    if (Intrinsics.areEqual(parameterTypes.length == 1 ? parameterTypes[0] : null, Throwable.class)) {
                        break;
                    }
                }
                i2++;
            }
            addSuppressed = method2;
            int length2 = methods.length;
            while (true) {
                if (i >= length2) {
                    break;
                }
                Method method3 = methods[i];
                if (Intrinsics.areEqual(method3.getName(), "getSuppressed")) {
                    method = method3;
                    break;
                }
                i++;
            }
            getSuppressed = method;
        }

        private ReflectThrowable() {
        }
    }

    public void addSuppressed(Throwable th, Throwable th2) {
        Method method = ReflectThrowable.addSuppressed;
        if (method != null) {
            method.invoke(th, th2);
        }
    }

    public List getSuppressed(Throwable th) {
        Object invoke;
        List asList;
        Method method = ReflectThrowable.getSuppressed;
        return (method == null || (invoke = method.invoke(th, null)) == null || (asList = Arrays.asList((Throwable[]) invoke)) == null) ? EmptyList.INSTANCE : asList;
    }
}
