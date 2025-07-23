package android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface SpecialUsers {

    @Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CanBeALL {
    }

    @Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CanBeCURRENT {
    }

    @Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CanBeNULL {
    }

    @Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CanBeUsers {
        SpecialUser[] specialUsersAllowed() default {SpecialUser.UNSPECIFIED};
    }

    @Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CannotBeSpecialUser {
    }

    public enum SpecialUser {
        USER_ALL,
        USER_CURRENT,
        USER_CURRENT_OR_SELF,
        USER_NULL,
        DISALLOW_USER_ALL,
        DISALLOW_USER_CURRENT,
        DISALLOW_USER_CURRENT_OR_SELF,
        DISALLOW_USER_NULL,
        ALLOW_EVERY,
        DISALLOW_EVERY,
        UNSPECIFIED
    }
}
