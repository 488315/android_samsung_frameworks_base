package android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Repeatable(Container.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestrictedFor {

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Container {
        RestrictedFor[] value();
    }

    Environment[] environments();

    int from();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static class Environment {
        public static final Environment SDK_SANDBOX = new AnonymousClass1("SDK_SANDBOX", 0);
        private static final /* synthetic */ Environment[] $VALUES = $values();

        private static /* synthetic */ Environment[] $values() {
            return new Environment[]{SDK_SANDBOX};
        }

        private Environment(String str, int i) {
        }

        public static Environment valueOf(String name) {
            return (Environment) Enum.valueOf(Environment.class, name);
        }

        public static Environment[] values() {
            return (Environment[]) $VALUES.clone();
        }

        /* renamed from: android.annotation.RestrictedFor$Environment$1, reason: invalid class name */
        enum AnonymousClass1 extends Environment {
            private AnonymousClass1(String str, int i) {
                super(str, i);
            }

            @Override // java.lang.Enum
            public String toString() {
                return "SDK Runtime";
            }
        }
    }
}
