package android.ravenwood.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface RavenwoodClassLoadHook {
    public static final String LIBANDROID_LOADING_HOOK =
            "com.android.platform.test.ravenwood.runtimehelper.ClassLoadHook.onClassLoaded";

    String value();
}
