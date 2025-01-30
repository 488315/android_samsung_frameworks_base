package kotlin.internal;

import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PlatformImplementations {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ReflectThrowable {
        public static final Method addSuppressed;

        /* JADX WARN: Code restructure failed: missing block: B:10:0x002d, code lost:
        
            if (kotlin.jvm.internal.Intrinsics.areEqual(r7.length == 1 ? r7[0] : null, java.lang.Throwable.class) != false) goto L14;
         */
        static {
            Method method;
            boolean z;
            new ReflectThrowable();
            Method[] methods = Throwable.class.getMethods();
            int length = methods.length;
            int i = 0;
            while (true) {
                method = null;
                if (i >= length) {
                    break;
                }
                Method method2 = methods[i];
                if (Intrinsics.areEqual(method2.getName(), "addSuppressed")) {
                    Class<?>[] parameterTypes = method2.getParameterTypes();
                    z = true;
                }
                z = false;
                if (z) {
                    method = method2;
                    break;
                }
                i++;
            }
            addSuppressed = method;
            int length2 = methods.length;
            for (int i2 = 0; i2 < length2 && !Intrinsics.areEqual(methods[i2].getName(), "getSuppressed"); i2++) {
            }
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

    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }
}
