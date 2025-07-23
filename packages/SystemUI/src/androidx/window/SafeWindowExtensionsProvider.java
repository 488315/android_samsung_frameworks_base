package androidx.window;

import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SafeWindowExtensionsProvider {
    public final ClassLoader loader;

    public SafeWindowExtensionsProvider(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    public final boolean isWindowExtensionsValid$window_release() {
        ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
        Function0 function0 = new Function0() { // from class: androidx.window.SafeWindowExtensionsProvider$isWindowExtensionsPresent$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SafeWindowExtensionsProvider.this.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider");
            }
        };
        reflectionUtils.getClass();
        try {
            function0.invoke();
            return ReflectionUtils.validateReflection$window_release("WindowExtensionsProvider#getWindowExtensions is not valid", new Function0() { // from class: androidx.window.SafeWindowExtensionsProvider$isWindowExtensionsValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean z = false;
                    Class[] clsArr = new Class[0];
                    Method declaredMethod = SafeWindowExtensionsProvider.this.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider").getDeclaredMethod("getWindowExtensions", null);
                    Class<?> loadClass = SafeWindowExtensionsProvider.this.loader.loadClass("androidx.window.extensions.WindowExtensions");
                    ReflectionUtils.INSTANCE.getClass();
                    if (declaredMethod.getReturnType().equals(loadClass) && Modifier.isPublic(declaredMethod.getModifiers())) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            });
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            return false;
        }
    }
}
