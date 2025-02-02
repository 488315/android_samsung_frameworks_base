package kotlin.jvm.internal;

import kotlin.reflect.KClass;

/* loaded from: classes.dex */
public final class Reflection {
    private static final KClass[] EMPTY_K_CLASS_ARRAY;
    private static final ReflectionFactory factory;

    static {
        ReflectionFactory reflectionFactory = null;
        try {
            reflectionFactory = (ReflectionFactory) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (reflectionFactory == null) {
            reflectionFactory = new ReflectionFactory();
        }
        factory = reflectionFactory;
        EMPTY_K_CLASS_ARRAY = new KClass[0];
    }

    public static ClassReference getOrCreateKotlinClass() {
        factory.getClass();
        return new ClassReference();
    }

    public static String renderLambdaToString(Lambda lambda) {
        factory.getClass();
        return ReflectionFactory.renderLambdaToString(lambda);
    }
}
