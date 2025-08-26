package kotlin.internal.jdk7;

import java.util.Arrays;
import java.util.List;
import kotlin.internal.PlatformImplementations;

/* loaded from: classes4.dex */
public class JDK7PlatformImplementations extends PlatformImplementations {

    public final class ReflectSdkVersion {
        public static final Integer sdkVersion;

        static {
            Object obj;
            new ReflectSdkVersion();
            Integer num = null;
            try {
                obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Throwable unused) {
            }
            Integer num2 = obj instanceof Integer ? (Integer) obj : null;
            if (num2 != null && num2.intValue() > 0) {
                num = num2;
            }
            sdkVersion = num;
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
