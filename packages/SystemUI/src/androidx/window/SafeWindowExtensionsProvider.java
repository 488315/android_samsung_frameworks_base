package androidx.window;

import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.jvm.functions.Function0;

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
                return this.this$0.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider");
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
                public final Object invoke() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
                    boolean z = false;
                    Class[] clsArr = new Class[0];
                    Method declaredMethod = this.this$0.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider").getDeclaredMethod("getWindowExtensions", null);
                    Class<?> clsLoadClass = this.this$0.loader.loadClass("androidx.window.extensions.WindowExtensions");
                    ReflectionUtils.INSTANCE.getClass();
                    if (declaredMethod.getReturnType().equals(clsLoadClass) && Modifier.isPublic(declaredMethod.getModifiers())) {
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
