package kotlin.random;

import java.io.Serializable;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.internal.jdk8.JDK8PlatformImplementations;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.random.jdk8.PlatformThreadLocalRandom;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class Random {
    public static final Default Default = new Default(null);
    public static final AbstractPlatformRandom defaultRandom;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Default extends Random implements Serializable {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
