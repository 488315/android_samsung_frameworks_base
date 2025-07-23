package android.ravenwood.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface RavenwoodIgnore {
    Class<?>[] blockedBy() default {};

    long bug() default 0;

    String reason() default "";
}
