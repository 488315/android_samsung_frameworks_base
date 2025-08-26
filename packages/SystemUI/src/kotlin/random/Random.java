package kotlin.random;

import java.io.Serializable;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.internal.jdk8.JDK8PlatformImplementations;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.random.jdk8.PlatformThreadLocalRandom;

/* loaded from: classes4.dex */
public abstract class Random {
    public static final Default Default = new Default(null);
    public static final AbstractPlatformRandom defaultRandom;

    public final class Default extends Random implements Serializable {

        final class Serialized implements Serializable {
            public static final Serialized INSTANCE = new Serialized();
            private static final long serialVersionUID = 0;

            private Serialized() {
            }

            private final Object readResolve() {
                return Random.Default;
            }
        }

        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Object writeReplace() {
            return Serialized.INSTANCE;
        }

        private Default() {
        }
    }

    static {
        PlatformImplementationsKt.IMPLEMENTATIONS.getClass();
        Integer num = JDK8PlatformImplementations.ReflectSdkVersion.sdkVersion;
        defaultRandom = (num == null || num.intValue() >= 34) ? new PlatformThreadLocalRandom() : new FallbackThreadLocalRandom();
    }
}
