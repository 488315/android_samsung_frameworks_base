package kotlin.internal.jdk7;

import java.util.Arrays;
import java.util.List;
import kotlin.internal.PlatformImplementations;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class JDK7PlatformImplementations extends PlatformImplementations {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReflectSdkVersion {
        public static final Integer sdkVersion;

        static {
            Integer num;
            Object obj;
            new ReflectSdkVersion();
            Integer num2 = null;
            try {
                obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Throwable unused) {
            }
            if (obj instanceof Integer) {
                num = (Integer) obj;
                if (num != null && num.intValue() > 0) {
                    num2 = num;
                }
                sdkVersion = num2;
            }
            num = null;
            if (num != null) {
                num2 = num;
            }
            sdkVersion = num2;
        }

        private ReflectSdkVersion() {
        }
    }

    @Override // kotlin.internal.PlatformImplementations
    public final void addSuppressed(Throwable th, Throwable th2) {
        Integer num = ReflectSdkVersion.sdkVersion;
        if (num == null || num.intValue() >= 19) {
            th.addSuppressed(th2);
        } else {
            super.addSuppressed(th, th2);
        }
    }

    @Override // kotlin.internal.PlatformImplementations
    public final List getSuppressed(Throwable th) {
        Integer num = ReflectSdkVersion.sdkVersion;
        return (num == null || num.intValue() >= 19) ? Arrays.asList(th.getSuppressed()) : super.getSuppressed(th);
    }
}
