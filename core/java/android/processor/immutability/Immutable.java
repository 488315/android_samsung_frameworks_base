package android.processor.immutability;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface Immutable {

    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Ignore {
        String reason() default "";
    }

    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Policy {

        public enum Exception {
            FINAL_CLASSES_WITH_FINAL_FIELDS
        }

        Exception[] exceptions() default {};
    }
}
